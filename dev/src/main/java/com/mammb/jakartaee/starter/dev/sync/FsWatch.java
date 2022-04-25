package com.mammb.jakartaee.starter.dev.sync;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class FsWatch {

    private static final Logger log = Logger.getLogger(FsWatch.class.getName());

    private final WatchService watchService;
    private final Map<WatchKey, Path> watchKeys;
    private final Path watchRoot;
    private final WatchEvent.Kind<?>[] watchEvents;
    private final WatchEvent.Modifier modifier;
    private final List<Consumer<FsEvent>> listeners;


    private FsWatch(
            Path watchRoot,
            WatchEvent.Kind<?>[] watchEvents,
            List<Consumer<FsEvent>> listeners,
            WatchEvent.Modifier modifier) {
        this.watchRoot = Objects.requireNonNull(watchRoot);
        this.watchEvents = watchEvents;
        this.watchKeys = new HashMap<>();
        this.modifier = modifier;
        this.listeners = Objects.requireNonNull(listeners);
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static FsWatch of(Path watchRoot, List<Consumer<FsEvent>> listeners) {
        return new FsWatch(
                watchRoot,
                new WatchEvent.Kind<?>[] { ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY },
                listeners,
                SensitivityWatchEventModifier.MEDIUM);
    }

    public static FsWatch modifyOnlyOf(Path watchRoot) {
        return new FsWatch(
                watchRoot,
                new WatchEvent.Kind<?>[] { ENTRY_MODIFY },
                new ArrayList<>(),
                SensitivityWatchEventModifier.MEDIUM);
    }

    public static FsWatch sensitiveOf(Path watchRoot) {
        return new FsWatch(
                watchRoot,
                new WatchEvent.Kind<?>[] { ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY },
                new ArrayList<>(),
                SensitivityWatchEventModifier.HIGH);
    }

    public FsWatch add(Consumer<FsEvent> listener) {
        listeners.add(listener);
        return this;
    }


    public void watch() {
        registerRecursive(watchRoot);
        try {
            for (;;) {

                var watchKey = watchService.take();
                var dir = watchKeys.get(watchKey);
                if (Objects.isNull(dir)) {
                    continue;
                }

                var events = new TreeSet<FsEvent>();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    if (event.kind() == OVERFLOW) {
                        continue;
                    }
                    if (!(event.context() instanceof Path)) {
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                    WatchEvent.Kind<Path> kind = (WatchEvent.Kind<Path>) event.kind();

                    var absPath = dir.resolve((Path) event.context());
                    if (Files.isDirectory(absPath)) {
                        if (kind == ENTRY_CREATE) {
                            registerRecursive(absPath);
                        } else if (kind == ENTRY_DELETE) {
                            var removed = watchKeys.remove(watchKey);
                            if (Objects.nonNull(removed)) {
                                var children = watchKeys.entrySet().stream()
                                    .filter(e -> e.getValue().startsWith(removed))
                                    .map(Map.Entry::getKey)
                                    .toList();
                                children.forEach(watchKeys::remove);
                            }
                        } else if (kind == ENTRY_MODIFY) {
                            registerRecursive(absPath);
                        }
                    } else {
                        events.add(new FsEvent(kind, absPath));
                    }

                }
                if (watchKeys.containsKey(watchKey)) {
                    watchKey.reset();
                }
                if (!events.isEmpty()) {
                    events.forEach(e -> listeners.forEach(l -> l.accept(e)));
                }
            }

        } catch (InterruptedException ignore) {
            log.warning("#### watchService interrupted.");
        }
    }


    private void registerRecursive(final Path root) {

        if (!root.toFile().exists() || !root.toFile().isDirectory()) {
            throw new RuntimeException("[" + root + "] does not exist or is not a directory");
        }

        try {
            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    if (watchKeys.containsValue(dir)) {
                        return FileVisitResult.CONTINUE;
                    }
                    var watchKey = dir.register(watchService, watchEvents, modifier);
                    watchKeys.put(watchKey, dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            watchService.close();
        } catch (IOException ignore) {
            log.warning("#### Can't close watchService" + ignore.getMessage());
        }
    }

}

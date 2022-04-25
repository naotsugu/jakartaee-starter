package com.mammb.jakartaee.starter.dev.sync;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class FsSync implements Consumer<FsEvent> {

    private static final Logger log = Logger.getLogger(FsSync.class.getName());

    private final Path source;
    private final Path dest;
    private final FsWatch fsWatch;


    protected FsSync(Path source, Path dest, FsWatch fsWatch) {
        this.source = source;
        this.dest = dest;
        this.fsWatch = fsWatch.add(this);
    }


    public static FsSync of(Path source, Path dest) {
        return new FsSync(source, dest, FsWatch.sensitiveOf(source));
    }

    public void start() {
        log.info("#### Start watch service. watch path[" + source + "]");
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
        fsWatch.watch();
    }

    public void stop() {
        fsWatch.close();
    }

    @Override
    public void accept(FsEvent fsEvent) {
        log.info("#### Detect " + fsEvent);
        var relative = source.relativize(fsEvent.path());
        if (fsEvent.isDeleted()) {
            delete(dest.resolve(relative));
        } else if (fsEvent.isCreated()) {
            copy(fsEvent.path(), dest.resolve(relative));
        } else if (fsEvent.isModified()) {
            replace(fsEvent.path(), dest.resolve(relative));
        }
    }

    static void delete(Path path) {
        try {
            if (Files.isDirectory(path)) {
                Files.list(path).forEach(FsSync::delete);
            }
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void copy(Path source, Path target) {
        try {
            Files.copy(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void replace(Path source, Path target) {
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

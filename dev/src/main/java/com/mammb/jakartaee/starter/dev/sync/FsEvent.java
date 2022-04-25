package com.mammb.jakartaee.starter.dev.sync;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public record FsEvent(WatchEvent.Kind<Path> kind, Path path) implements Comparable<FsEvent> {

    @Override
    public int compareTo(FsEvent other) {
        return path.compareTo(other.path);
    }

    public boolean isCreated() {
        return kind == ENTRY_CREATE;
    }

    public boolean isModified() {
        return kind == ENTRY_MODIFY;
    }

    public boolean isDeleted() {
        return kind == ENTRY_DELETE;
    }

}

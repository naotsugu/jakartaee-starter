package com.mammb.jakartaee.starter.dev.sync;

import com.mammb.jakartaee.starter.dev.server.EmbeddedFish;

import java.nio.file.Path;
import java.util.logging.Logger;

public class ArchiveSync extends FsSync {

    private static final Logger log = Logger.getLogger(ArchiveSync.class.getName());

    private final EmbeddedFish fish;

    private ArchiveSync(EmbeddedFish fish, Path source, Path dest) {
        super(source, dest, FsWatch.modifyOnlyOf(source));
        this.fish = fish;
    }

    public static ArchiveSync of(EmbeddedFish fish, Path source, Path dest) {
        return new ArchiveSync(fish, source, dest);
    }

    @Override
    public void accept(FsEvent fsEvent) {
        super.accept(fsEvent);
        try {
            fish.reDeploy();
        } catch (Exception e) {
            log.warning("#### " + e.getMessage());
        }
    }

}

package com.mammb.jakartaee.starter.dev.server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Archive {

    final Path dir;
    final Type type;
    final Path path;

    private Archive(Path dir) {
        this.dir = dir;
        InputStream is = Archive.class.getResourceAsStream("/" + Type.EAR.name);
        if (Objects.nonNull(is)) {
            type = Type.EAR;
        } else {
            is = Archive.class.getResourceAsStream("/" + Type.WAR.name);
            type = Type.WAR;
        }

        try {
            Files.createDirectories(dir);
            path = dir.resolve(type.name);
            Files.copy(is, path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Archive of(Path root) {
        return new Archive(root);
    }

    enum Type {
        EAR("ear.ear", Path.of("../ear/build/libs/"), Paths.get("../web/src/main/webapp/")),
        WAR("web.war", Path.of("../web/build/libs/"), Paths.get("../web/src/main/webapp/"));
        final String name;
        final Path buildArchiveDir;
        final Path webappSourceDir;
        Type(String name, Path buildArchiveDir, Path webappSourceDir) {
            this.name = name;
            this.buildArchiveDir = buildArchiveDir;
            this.webappSourceDir = webappSourceDir;
        }
    }

}

package com.mammb.jakartaee.starter.dev.server;

import com.mammb.jakartaee.starter.dev.sync.ArchiveSync;
import com.mammb.jakartaee.starter.dev.sync.FsSync;
import com.mammb.jakartaee.starter.dev.sync.SyncService;
import java.awt.Desktop;
import java.net.URI;
import java.nio.file.Path;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    private static final boolean dev = System.getProperty("env", "").equals("dev");


    public static void main(String[] args) throws Exception {

        LogManager.getLogManager().readConfiguration(
            Main.class.getResourceAsStream("/conf/logging.properties"));

        var fish = EmbeddedFish.of();
        fish.start();
        Runtime.getRuntime().addShutdownHook(new Thread(fish::stop));

        var archive = Archive.of(dev
                ? Path.of("./build/tmp/archives/")
                : Path.of("./archives/"));
        fish.deploy(archive.path);

        if (dev) {

            openBrowser(fish.port, fish.contextRoot);

            var syncService = SyncService.of();
            syncService.add(FsSync.of(
                    archive.type.webappSourceDir,
                    fish.webAppRoot()));
            syncService.add(ArchiveSync.of(fish,
                    archive.type.buildArchiveDir,
                    archive.dir));
        }
    }

    private static void openBrowser(int port, String contextRoot) {
        try {
            var headless = System.getProperty("java.awt.headless");
            if ("true".equals(headless)) {
                System.setProperty("java.awt.headless", "false");
            }
            var uri = new URI(String.format("http://localhost:%s/%s/index.html", port, contextRoot));
            Desktop.getDesktop().browse(uri);

            if ("true".equals(headless)) {
                System.setProperty("java.awt.headless", headless);
            }
        } catch (Exception ignore) {
            log.warning(ignore.getMessage());
        }
    }
}

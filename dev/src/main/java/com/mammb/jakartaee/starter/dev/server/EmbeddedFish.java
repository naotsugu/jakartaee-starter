package com.mammb.jakartaee.starter.dev.server;

import org.glassfish.embeddable.BootstrapProperties;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Logger;

public class EmbeddedFish {

    private static final Logger log = Logger.getLogger(EmbeddedFish.class.getName());

    private static final boolean dev = System.getProperty("env", "").equals("dev");

    private final Path instanceRoot;
    final int port;
    private final GlassFish glassFish;
    final String contextRoot;
    final String appName;
    private Path deployedSource;


    private EmbeddedFish(String appName, int port, GlassFish glassFish, Path instanceRoot) {
        this.contextRoot = "web";
        this.appName = appName;
        this.port = port;
        this.glassFish = glassFish;
        this.instanceRoot = instanceRoot;
    }


    public static EmbeddedFish of() throws Exception {
        var port = 8080;
        var path = Path.of(dev ? "./build/tmp/embedded" : "./");
        var gf = GlassFishRuntime
                .bootstrap(bootstrapProperties())
                .newGlassFish(glassFishProperties(path, port));
        return new EmbeddedFish("starter", port, gf, path);
    }


    void start() throws Exception {
        glassFish.start();
    }


    public String deploy(Path archive) throws Exception {
        if (glassFish.getStatus() != GlassFish.Status.STARTED) {
            return "";
        }
        this.deployedSource = archive;
        var name = glassFish.getDeployer()
                .deploy(deployedSource.toFile(),
                        "--name=" + appName,
                        "--contextroot=" + contextRoot,
                        "--force=true");
        log.info("#### Complete deploy.[" + name + "]");
        return name;
    }


    public void reDeploy() throws Exception {
        undeploy();
        deploy(deployedSource);
    }


    public void undeploy() throws Exception {
        if (glassFish.getStatus() != GlassFish.Status.STARTED) {
            return;
        }
        glassFish.getDeployer().undeploy(appName, "--cascade=true");
        log.info("#### Complete undeploy.[" + appName + "]");
    }


    void stop() {
        try {
            glassFish.dispose();
            deleteRecursively(instanceRoot);
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }


    public Path appRoot() {
        return appRoot(instanceRoot);
    }


    public Path webAppRoot() {
        return deployedSource.toString().endsWith(".ear")
                ? appRoot().resolve("applications/" + appName + "/web_war/")
                : appRoot().resolve("applications/" + appName + "/");
    }


    public void runCommand(String command, String... args) throws Exception {
        var commandRunner = glassFish.getCommandRunner();
        var result = commandRunner.run(command, args);
        log.info("#### ExitStatus : " + result.getExitStatus());
        log.info("#### Output : " + result.getOutput());
    }


    private static BootstrapProperties bootstrapProperties() {
        return new BootstrapProperties();
    }


    private static GlassFishProperties glassFishProperties(Path instanceRoot, int port) {
        var properties = new GlassFishProperties();
        properties.setPort("http-listener", port);
        //properties.setPort("https-listener", 8184);
        properties.setProperty("glassfish.embedded.tmpdir", instanceRoot.toString());
        return properties;
    }


    private static void deleteRecursively(Path pathToBeDelete) throws IOException {
        Files.walk(pathToBeDelete)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }


    private static Path appRoot(Path embeddedRoot) {
        final File[] files = embeddedRoot.toFile().listFiles();
        return Arrays.stream(Objects.requireNonNull(files))
                .filter(File::isDirectory)
                .filter(file -> file.getName().startsWith("gfembed"))
                .sorted(Comparator.comparingLong(File::lastModified))
                .map(File::toPath)
                .findFirst()
                .orElseThrow();
    }

}

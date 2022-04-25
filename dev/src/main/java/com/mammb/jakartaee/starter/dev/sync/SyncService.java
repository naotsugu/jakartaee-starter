package com.mammb.jakartaee.starter.dev.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SyncService {

    private static final Logger log = Logger.getLogger(SyncService.class.getName());

    private final ExecutorService executor;

    public SyncService(ExecutorService executor) {
        this.executor = executor;
    }

    public static SyncService of() {
        return new SyncService(Executors.newFixedThreadPool(2));
    }

    public void add(FsSync fsSync) {
        executor.submit(fsSync::start);
    }

}

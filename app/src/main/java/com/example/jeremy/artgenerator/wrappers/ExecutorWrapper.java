package com.example.jeremy.artgenerator.wrappers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorWrapper {

    private Executor executor;

    public ExecutorWrapper(int countOfThreads) {
        executor = Executors.newFixedThreadPool(countOfThreads);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}

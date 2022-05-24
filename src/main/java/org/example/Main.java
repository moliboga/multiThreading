package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/enwik8.txt");

        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        MyThread myThread = new MyThread(reader);
//        myThread.start();
//        MyThread myThread2 = new MyThread(reader);
//        myThread2.start();

//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        for (int i = 0; i < 20; i++){
//            executorService.submit(new MyRunnable(reader));
//        }
//        executorService.shutdown();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++){
            executorService.submit(new MyCallable(reader));
        }
        executorService.shutdown();
    }
}


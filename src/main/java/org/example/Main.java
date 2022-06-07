package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("src/main/resources/enwik8.txt");

        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int counter = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++){
            Future<Integer> future = executorService.submit(new MyCallable(reader));
            counter += future.get();
        }
        executorService.shutdown();

//        for (int i = 0; i < 1000000; i++) {
//            String line = reader.readLine();
//            counter += line.split(" ").length;
//        } // 12875192

        System.out.println(counter);
    }
}


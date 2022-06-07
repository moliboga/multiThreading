package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static int doSomething()
            throws ExecutionException, InterruptedException, IOException {

        Path path = Paths.get("src/main/resources/enwik8.txt");
        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int variant = 2;
        int counter = 0;
        long time = System.currentTimeMillis();

        if (variant == 0){
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 4; i++){
                Future<Integer> future = executorService.submit(new MyCallable(reader));
                counter += future.get();
            }
            executorService.shutdown();
        }
        else if (variant == 1){
            for (int i = 0; i < 1000000; i++) {
                String line = reader.readLine();
                counter += line.split(" ").length;
            } // 12875192
        }
        else if (variant == 2){
            List<MyThread> threads = new ArrayList<>();
            for (int idx = 0; idx < 2; ++idx) {
                threads.add(new MyThread(reader));
                threads.get(idx).start();
            }
            int total = 0;
            for (MyThread thread : threads) {
                thread.join();
                total += thread.getCounter();
            }
            counter = total;
        }

        System.out.println(System.currentTimeMillis() - time);
        return counter;
    }
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        System.out.println(doSomething());
    }
}


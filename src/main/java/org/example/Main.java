package org.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static int doSomething()
            throws ExecutionException, InterruptedException, IOException {

        long time = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger();

        int chunks = Runtime.getRuntime().availableProcessors();
        long[] offsets = new long[chunks];
        File file = new File("src/main/resources/enwik8.txt");

        // determine line boundaries for number of chunks
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        for (int i = 1; i < chunks; i++) {
            raf.seek(i * file.length() / chunks);
            while (true) {
                int read = raf.read();
                if (read == '\n' || read == -1) {
                    break;
                }
            }
            offsets[i] = raf.getFilePointer();
        }
        raf.close();

        // process each chunk using a thread for each one
        ExecutorService executorService = Executors.newFixedThreadPool(chunks);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < chunks; i++) {
            long start = offsets[i];
            long end = i < chunks - 1 ? offsets[i + 1] : file.length();
            tasks.add(new FileProcessor(file, start, end));
        }
        List<Future<Integer>> results = executorService.invokeAll(tasks);
        executorService.shutdown();

        results.forEach(result -> {
            try {
                counter.addAndGet(result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counter.get());

        System.out.println(System.currentTimeMillis() - time);
        return counter.get();
    }
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        System.out.println(doSomething());
    }
}


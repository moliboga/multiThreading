package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    private final BufferedReader reader;

    public MyCallable(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 100000; i++) {
            try {
                String line = reader.readLine();
                char[] ch = line.toCharArray();
                Bubble.bubbleSort(ch);
//                System.out.println((new String(ch)).trim());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}

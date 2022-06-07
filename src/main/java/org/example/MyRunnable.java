package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class MyRunnable implements Runnable {

    private final BufferedReader reader;
    private volatile int counter;

    public MyRunnable(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500000; i++) {
            try {
                String line = reader.readLine();
                counter += line.split(" ").length;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getCounter() {
        return counter;
    }
}

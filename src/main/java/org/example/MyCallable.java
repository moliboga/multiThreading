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
    public Integer call() throws IOException {
        int counter = 0;
        for (int i = 0; i < 250000; i++) {
            String line = reader.readLine();
            counter += line.split(" ").length;
        }
        return counter;
    }
}

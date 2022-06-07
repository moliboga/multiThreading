package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyThread extends Thread {

    private final BufferedReader reader;
    private int counter;
    public MyThread(BufferedReader reader) {
        this.reader = reader;
        counter = 0;
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

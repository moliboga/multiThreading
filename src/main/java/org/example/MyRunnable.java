package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class MyRunnable implements Runnable {

    private final BufferedReader reader;

    public MyRunnable(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50000; i++) {
            try {
                String line = reader.readLine();
                char[] ch = line.toCharArray();
                Bubble.bubbleSort(ch);
//                System.out.println((new String(ch)).trim());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

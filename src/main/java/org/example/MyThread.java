package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyThread extends Thread {

    private final BufferedReader reader;
    public MyThread(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500000; i++){
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

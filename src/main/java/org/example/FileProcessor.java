package org.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;

public class FileProcessor implements Callable {
    private final File file;
    private final long start;
    private final long end;

    public FileProcessor(File file, long start, long end) {
        this.file = file;
        this.start = start;
        this.end = end;
    }

    public Integer call() {
        int counter = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(start);
            while (raf.getFilePointer() < end) {
                if (raf.getFilePointer() % 1000000 < 100){
                    System.out.println(raf.getFilePointer());
                }
                String line = raf.readLine();
                if (line == null) {
                    continue;
                }
                counter += line.split(" ").length;
            }
            raf.close();
        } catch (IOException e) {
            // deal with exception
        }
        return counter;
    }
}

package com.basejava.webapp;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File("./.gitignore");
        System.out.println(file.getCanonicalPath());
        File dir = new File(".\\src\\com\\basejava\\webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }


        /////////////////////////   HW8   /////////////////////////
        MainFile m = new MainFile();
        m.allFiles();
    }

    private void allFiles() throws IOException {
        List<String> list = new ArrayList<>();
        Queue<File> queue = new ArrayDeque<>();
        File root = new File("..\\basejava");
        queue.add(root);
        while (!queue.isEmpty()) {
            File currentFile = queue.poll();
            Objects.requireNonNull(currentFile);
            if (currentFile.isDirectory()) {
                File[] files = currentFile.listFiles();
                Objects.requireNonNull(files);
                for (File file : files) {
                    if (file.isDirectory()) {
                        queue.add(file);
                    }
                    list.add(file.getCanonicalPath());
                }
            }
        }
        for (String s : list) {
            System.out.println(s);
        }
    }
}

package com.basejava.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File("./.gitignore");
        System.out.println(file.getCanonicalPath());
        File dir = new File(".\\src");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        printDeepDirectory(dir);
    }

    public static StringBuffer offset = new StringBuffer();

    private static void printDeepDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(offset + "->File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(offset + "*Directory: " + file.getName());
                    offset.append("\t");
                    printDeepDirectory(file);
                    offset.deleteCharAt(offset.length() - 1);
                }
            }
        }
    }
}



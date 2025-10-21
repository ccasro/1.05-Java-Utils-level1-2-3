package org.ccasro.level1;

import java.io.File;

public class Exercise1 {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: java Exercise1 <directory_path>");
            System.exit(-1);
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The given path is not a valid directory");
            return;
        }

        File[] files = directory.listFiles();
        if (files != null){
            System.out.println("Contents of: " + directory.getPath());
            for(File file : files){
                System.out.println(file.getName());
            }
        }
    }
}

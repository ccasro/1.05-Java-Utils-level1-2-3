package org.ccasro.level1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Exercise2 {
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
        
        listDirectory(directory, 0);

        File[] files = directory.listFiles();
        if (files != null){
            System.out.println("Contents of: " + directory.getPath());
            for(File file : files){
                System.out.println(file.getName());
            }
        }
    }

    private static void listDirectory(File directory, int level) {
        File[] files = directory.listFiles();
        if (files == null) return;

        Arrays.sort(files);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String indent = "   ".repeat(level);

        for (File file: files){
            String type = file.isDirectory() ? "(D)" : "(F)";
            String lastModified = sdf.format(new Date(file.lastModified()));
            System.out.println(indent + type + " " + file.getName() + " - " + lastModified);

            if (file.isDirectory()) {
                listDirectory(file,level+1);
            }
        }
    }
}

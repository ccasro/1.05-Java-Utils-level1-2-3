package org.ccasro.level1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Exercise3 {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Usage: java Exercise3 <directory_path> <output_file>");
            System.exit(-1);
        }

        String directoryPath = args[0];
        String outputFilePath = args[1];
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The given path is not a valid directory");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            listDirectory(directory, 0,writer);
            System.out.println("Directory tree saved to " + outputFilePath);
        } catch (IOException e){
            System.err.println("Error writing to file: "  + e.getMessage());
        }

        File[] files = directory.listFiles();
        if (files != null){
            System.out.println("Contents of: " + directory.getPath());
            for(File file : files){
                System.out.println(file.getName());
            }
        }
    }

    private static void listDirectory(File directory, int level, BufferedWriter writer) throws IOException {
        File[] files = directory.listFiles();
        if (files == null) return;

        Arrays.sort(files);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String indent = "   ".repeat(level);

        for (File file: files){
            String type = file.isDirectory() ? "(D)" : "(F)";
            String lastModified = sdf.format(new Date(file.lastModified()));
            writer.write(indent + type + " " + file.getName() + " - " + lastModified);
            writer.newLine();

            if (file.isDirectory()) {
                listDirectory(file,level+1,writer);
            }
        }
    }
}

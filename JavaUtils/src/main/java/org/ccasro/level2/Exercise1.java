package org.ccasro.level2;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class Exercise1 {
    public static void main(String[] args) {

        Properties props = new Properties();

        try (FileReader reader = new FileReader("JavaUtils/config/settings.properties")) {
            props.load(reader);
        } catch (IOException e){
            System.err.println("Error loading configuration file: " + e.getMessage());
            return;
        }


        String directoryPath = props.getProperty("directory.path");
        String outputFilePath = props.getProperty("output.file");

        if (directoryPath == null || outputFilePath == null){
            System.err.println("Missing properties in configuration file.");
            return;
        }

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

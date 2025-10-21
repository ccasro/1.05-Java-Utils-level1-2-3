package org.ccasro.level1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Exercise4 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Exercise4 <text_file>");
            return;
        }

        String filePath = args[0];
        File file = new File(filePath);

        if(!file.exists() || !file.isFile()) {
            System.out.println("The given path is not a valid file.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            System.out.println("File content of: " + file.getName());
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

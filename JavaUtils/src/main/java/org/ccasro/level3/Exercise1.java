package org.ccasro.level3;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.ccasro.level3.AESFileUtil.*;

public class Exercise1 {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (FileReader reader = new FileReader("JavaUtils/config/settings.properties")) {
            props.load(reader);
        } catch (IOException e){
            System.err.println("Error loading configuration file: " + e.getMessage());
            return;
        }

        String inputFile = props.getProperty("output.file");
        if (inputFile == null || inputFile.isEmpty()) {
            System.err.println("Property 'output.file' not found in settings.properties");
            return;
        }

        File input = new File(inputFile);
        if (!input.exists() || !input.isFile()) {
            System.err.println("Input file does not exist: " + inputFile);
            return;
        }

        String outputFile = input.getName() + ".enc";

        try {
            SecretKey key = generateKey(128);
            saveKey(key, "aeskey.txt");

            processFile(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);

            SecretKey loadedKey = loadKey("aeskey.txt");
            processFile(Cipher.DECRYPT_MODE, loadedKey, outputFile, "decrypted" + inputFile);
            System.out.println("Encryption / decryption completed.");
        } catch (Exception e) {
            System.err.println("Error during encryption/decryption: " + e.getMessage());
        }
    }
}

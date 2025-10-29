package org.ccasro.level3;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class AESFileUtil {
    public static SecretKey generateKey(int bits) throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(bits);
        return keyGen.generateKey();
    }

    public static void saveKey (SecretKey key, String keyFilePath) throws IOException {
        String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());
        try (FileWriter fw = new FileWriter(keyFilePath)){
            fw.write(keyBase64);
        }
    }

    public static SecretKey loadKey(String keyFilePath) throws IOException{
        String keyBase64;
        try (BufferedReader br = new BufferedReader(new FileReader(keyFilePath))) {
            keyBase64 = br.readLine();
        }
        byte[] decodedKey = Base64.getDecoder().decode(keyBase64);
        return new SecretKeySpec(decodedKey,0,decodedKey.length,"AES");
    }

    public static void processFile(int cipherMode, SecretKey key, String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(cipherMode, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) fos.write(output);
            }

            byte[] outputFinal = cipher.doFinal();
            if (outputFinal != null) fos.write(outputFinal);
        }
    }
}

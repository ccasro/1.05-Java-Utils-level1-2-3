package org.ccasro.level1;

import java.io.*;

public class Exercise5 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Exercise5 <output_file.ser>");
            return;
        }

        String outputfile = args[0];

        Item item = new Item("Chair",200.5);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputfile))) {
            oos.writeObject(item);
            System.out.println("Object serialized to " + outputfile);
        } catch (IOException e) {
            System.err.println("Error serializing object: " + e.getMessage());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputfile))) {
            Item deserializedItem = (Item) ois.readObject();
            System.out.println("Object deserialized");
            System.out.println(deserializedItem);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing object: " + e.getMessage());
        }
    }
}

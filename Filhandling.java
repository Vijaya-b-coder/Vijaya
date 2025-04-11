import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileOperations {
    public static void main(String[] args) {
        String filePath = "myTextFile.txt";
        
        // Let's write something into the file
        writeToFile(filePath, "Hey there! This is a simple text file.\nHope you enjoy learning Java file handling.");
        
        // Now, let's read what we wrote
        readFromFile(filePath);
        
        // Finally, let's modify the file by adding some more text
        modifyFile(filePath, "\nHere is some extra text added to the file.");
    }

    // Method to write content into a file
    public static void writeToFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("Great! The file has been written successfully.");
        } catch (IOException e) {
            System.out.println("Oops! Something went wrong while writing to the file: " + e.getMessage());
        }
    }

    // Method to read content from a file
    public static void readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Let's check what's inside the file:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Couldn't read the file: " + e.getMessage());
        }
    }

    // Method to modify a file by adding new content at the end
    public static void modifyFile(String filePath, String newContent) {
        try (FileWriter writer = new FileWriter(filePath, true)) { // true enables append mode
            writer.write(newContent);
            System.out.println("Nice! The file has been updated successfully.");
        } catch (IOException e) {
            System.out.println("Something went wrong while modifying the file: " + e.getMessage());
        }
    }
}

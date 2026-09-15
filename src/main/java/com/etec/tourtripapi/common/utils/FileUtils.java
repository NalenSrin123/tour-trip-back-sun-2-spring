package com.etec.tourtripapi.common.utils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FileUtils {

    /**
     * Search for a file recursively in all upload subdirectories
     * @param fileName the name of the file to search for
     * @return list of paths where the file was found
     * @throws IOException if an I/O error occurs
     */
    public List<Path> searchFileInAllUploadDirs(String fileName) throws IOException {
        List<Path> foundFiles = new ArrayList<>();
        Path baseUploadDir = Paths.get("uploads/").toAbsolutePath().normalize();

        System.out.println("Recursively searching in: " + baseUploadDir);

        if (Files.exists(baseUploadDir)) {
            foundFiles = Files.walk(baseUploadDir)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(fileName))
                    .collect(Collectors.toList());

            if (!foundFiles.isEmpty()) {
                System.out.println("Found " + foundFiles.size() + " file(s) matching: " + fileName);
                for (Path path : foundFiles) {
                    System.out.println("  - " + path);
                }
            } else {
                System.out.println("No files found matching: " + fileName);
            }
        } else {
            System.out.println("Base upload directory does not exist: " + baseUploadDir);
        }

        return foundFiles;
    }

    /**
     * Search for a file in specific directories
     * @param fileName the name of the file to search for
     * @param directories list of directories to search
     * @return the first path where the file is found, or null if not found
     */
    public Path searchFileInDirectories(String fileName, List<String> directories) {
        for (String directory : directories) {
            Path path = Paths.get(directory).toAbsolutePath().normalize();
            Path filePath = path.resolve(fileName).normalize();

            System.out.println("Checking directory: " + filePath);

            if (Files.exists(filePath)) {
                System.out.println("Found file in: " + directory);
                return filePath;
            }
        }
        return null;
    }

    /**
     * Get all upload directories as a list
     * @return list of upload directory paths
     */
    public List<String> getAllUploadDirectories() {
        List<String> directories = new ArrayList<>();
        directories.add("uploads/brands/");
        directories.add("uploads/products/");
        directories.add("uploads/categories/");
        directories.add("uploads/");
        return directories;
    }

    /**
     * Check if a file exists in any upload directory
     * @param fileName the name of the file to check
     * @return true if file exists, false otherwise
     */
    public boolean isFileExistsInUploadDirs(String fileName) {
        try {
            List<Path> foundPaths = searchFileInAllUploadDirs(fileName);
            return !foundPaths.isEmpty();
        } catch (IOException e) {
            System.err.println("Error checking file existence: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the full path of a file in upload directories
     * @param fileName the name of the file to find
     * @return the full path of the file, or null if not found
     */
    public Path getFilePathInUploadDirs(String fileName) {
        try {
            List<Path> foundPaths = searchFileInAllUploadDirs(fileName);
            if (!foundPaths.isEmpty()) {
                return foundPaths.get(0);
            }
        } catch (IOException e) {
            System.err.println("Error finding file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Validate file type (only images allowed)
     * @param fileName the name of the file to validate
     * @return true if file type is valid image, false otherwise
     */
    public boolean isValidImageFile(String fileName) {
        if (fileName == null) return false;

        String lowerName = fileName.toLowerCase();
        return lowerName.endsWith(".jpg") ||
                lowerName.endsWith(".jpeg") ||
                lowerName.endsWith(".png") ||
                lowerName.endsWith(".gif") ||
                lowerName.endsWith(".webp") ||
                lowerName.endsWith(".bmp") ||
                lowerName.endsWith(".svg");
    }

    /**
     * Get file extension
     * @param fileName the name of the file
     * @return the file extension (e.g., ".jpg", ".png")
     */
    public String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * Generate a unique filename
     * @param originalFileName the original file name
     * @return unique filename with UUID
     */

    public String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        return UUID.randomUUID().toString() + extension;
    }
}
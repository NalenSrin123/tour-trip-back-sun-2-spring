package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImp implements FileService {

    @Value("${app.images.base-dir:uploads/}")
    private String baseDir;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public String uploadFile(String uploadDir, MultipartFile file) throws IOException {
        // Debug logging
        System.out.println("=== Upload File ===");
        System.out.println("Upload directory: " + uploadDir);

        // Create directory if not exists
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("Created directory: " + uploadPath);
        }

        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IOException("File is empty or null");
        }

        // Get original filename and generate unique name
        String originalFileName = file.getOriginalFilename();
        String fileName = fileUtils.generateUniqueFileName(originalFileName);

        // Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Verify file was saved
        System.out.println("File saved to: " + filePath);
        System.out.println("File size: " + Files.size(filePath) + " bytes");
        System.out.println("File name: " + fileName);

        return fileName;
    }

    @Override
    public InputStream getResource(String uploadDir, String name) throws FileNotFoundException {
        Path filePath = Paths.get(uploadDir).toAbsolutePath().resolve(name).normalize();

        System.out.println("Getting resource from: " + filePath);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + name + " in directory: " + uploadDir);
        }

        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new FileNotFoundException("Unable to read file: " + name + " - " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllFiles(String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            return new ArrayList<>();
        }

        return Files.list(uploadPath)
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String uploadDir, String image) {
        try {
            if (uploadDir == null || uploadDir.trim().isEmpty()) {
                throw new IllegalArgumentException("Upload directory cannot be null or empty");
            }

            if (image == null || image.trim().isEmpty()) {
                throw new IllegalArgumentException("Image filename cannot be null or empty");
            }

            // Security: Prevent path traversal
            String normalizedImage = Paths.get(image).normalize().toString();
            if (normalizedImage.contains("..")) {
                throw new SecurityException("Path traversal not allowed");
            }

            // Create full file path
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(normalizedImage).normalize();

            System.out.println("Deleting file: " + filePath);

            // Security: Ensure within upload directory
            if (!filePath.startsWith(uploadPath)) {
                throw new SecurityException("Cannot delete files outside upload directory");
            }

            // Delete the file
            boolean deleted = Files.deleteIfExists(filePath);

            if (!deleted) {
                throw new FileNotFoundException("File not found: " + image + " in directory: " + uploadDir);
            }

            System.out.println("File deleted successfully: " + filePath);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage(), e);
        } catch (SecurityException e) {
            throw new RuntimeException("Security violation: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteFileWithResult(String uploadDir, String image) {
        try {
            deleteFile(uploadDir, image);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to delete file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteMultipleFiles(String uploadDir, List<String> images) {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("Images list cannot be null or empty");
        }

        List<String> failedDeletions = new ArrayList<>();

        for (String image : images) {
            try {
                deleteFile(uploadDir, image);
            } catch (Exception e) {
                failedDeletions.add(image);
                System.err.println("Failed to delete: " + image + " - " + e.getMessage());
            }
        }

        if (!failedDeletions.isEmpty()) {
            throw new RuntimeException("Failed to delete files: " + failedDeletions);
        }
    }

    @Override
    public void deleteAllFiles(String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            throw new FileNotFoundException("Directory not found: " + uploadDir);
        }

        // Delete all files in directory
        Files.list(uploadPath)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        Files.delete(file);
                        System.out.println("Deleted: " + file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete file: " + file, e);
                    }
                });
        System.out.println("All files deleted from: " + uploadDir);
    }

    @Override
    public byte[] getFileAsByteArray(String uploadDir, String fileName) {
        try {
            // Normalize and get absolute paths
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();

            // Debug logging
            System.out.println("=== Get File As Byte Array ===");
            System.out.println("Upload directory: " + uploadDir);
            System.out.println("Upload path (absolute): " + uploadPath);
            System.out.println("File name: " + fileName);
            System.out.println("Full file path: " + filePath);
            System.out.println("File exists: " + Files.exists(filePath));

            // Try alternative paths if file not found
            if (!Files.exists(filePath)) {
                // Try with original path without absolute conversion
                Path originalPath = Paths.get(uploadDir).resolve(fileName);
                System.out.println("Trying original path: " + originalPath);

                if (Files.exists(originalPath)) {
                    filePath = originalPath;
                    System.out.println("Found file at original path");
                } else {
                    // Try relative to working directory
                    Path workingDir = Paths.get("").toAbsolutePath();
                    Path relativePath = workingDir.resolve(uploadDir).resolve(fileName);
                    System.out.println("Trying relative path: " + relativePath);

                    if (Files.exists(relativePath)) {
                        filePath = relativePath;
                        System.out.println("Found file at relative path");
                    } else {
                        // Use FileUtils to search in all directories
                        Path foundPath = fileUtils.searchFileInDirectories(fileName, fileUtils.getAllUploadDirectories());

                        if (foundPath != null) {
                            filePath = foundPath;
                            System.out.println("Found file via FileUtils search: " + filePath);
                        } else {
                            // Try recursive search as last resort
                            List<Path> foundPaths = fileUtils.searchFileInAllUploadDirs(fileName);
                            if (!foundPaths.isEmpty()) {
                                filePath = foundPaths.get(0);
                                System.out.println("Found file via recursive search: " + filePath);
                            } else {
                                throw new FileNotFoundException(
                                        String.format("File not found: %s (searched in all upload directories)", fileName)
                                );
                            }
                        }
                    }
                }
            }

            // Security check
            Path allowedPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!filePath.startsWith(allowedPath) &&
                    !filePath.startsWith(Paths.get(uploadDir).normalize()) &&
                    !filePath.toString().contains("uploads/")) {
                throw new SecurityException("Cannot access files outside upload directory");
            }

            byte[] bytes = Files.readAllBytes(filePath);
            System.out.println("File read successfully, size: " + bytes.length + " bytes");
            System.out.println("File source: " + filePath);

            return bytes;

        } catch (FileNotFoundException e) {
            System.err.println("File not found error: " + e.getMessage());
            throw new RuntimeException("Failed to read file: " + e.getMessage(), e);
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            throw new RuntimeException("Failed to read file: " + e.getMessage(), e);
        } catch (SecurityException e) {
            System.err.println("Security error: " + e.getMessage());
            throw new RuntimeException("Security violation: " + e.getMessage(), e);
        }
    }
}
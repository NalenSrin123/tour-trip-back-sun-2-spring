package com.etec.tourtripapi.tour.controller;

import com.etec.tourtripapi.tour.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    // កំណត់ path សម្រាប់ upload រូបភាព Tour (អាចកែសម្រួលក្នុង application.yml បាន)
    @Value("${app.images.tour-path:uploads/tours/}")
    private String tourUploadPath;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // View image in browser (inline)
    @GetMapping("/view/{fileName}")
    public ResponseEntity<?> viewImage(@PathVariable String fileName) {
        try {
            byte[] fileBytes = fileService.getFileAsByteArray(tourUploadPath, fileName);
            String contentType = determineContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                    .body(fileBytes);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "File not found: " + fileName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // Upload Tour image
    @PostMapping("/upload")
    public ResponseEntity<?> uploadTourImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileService.uploadFile(tourUploadPath, file);

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("url", baseUrl + "/api/files/view/" + fileName);
            response.put("message", "File uploaded successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Delete file
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        try {
            fileService.deleteFile(tourUploadPath, fileName);

            Map<String, String> response = new HashMap<>();
            response.put("message", "File deleted successfully: " + fileName);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    private String determineContentType(String fileName) {
        if (fileName == null) return "application/octet-stream";
        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) return "image/jpeg";
        if (lowerName.endsWith(".png")) return "image/png";
        if (lowerName.endsWith(".gif")) return "image/gif";
        if (lowerName.endsWith(".webp")) return "image/webp";
        return "application/octet-stream";
    }
}
package com.etec.tourtripapi.tour.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileService {
    String uploadFile(String uploadDir, MultipartFile file) throws IOException;

    InputStream getResource(String uploadDir, String name) throws FileNotFoundException;

    List<String> getAllFiles(String uploadDir) throws IOException;

    void deleteFile(String uploadDir, String image);

    boolean deleteFileWithResult(String uploadDir, String image);

    void deleteMultipleFiles(String uploadDir, List<String> images);

    void deleteAllFiles(String uploadDir) throws IOException;

    byte[] getFileAsByteArray(String uploadPath, String fileName);
}
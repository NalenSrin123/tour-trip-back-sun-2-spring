package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.tour.dto.request.ImageRequestDTO;
import com.etec.tourtripapi.tour.dto.response.ImageResponseDTO;
import com.etec.tourtripapi.tour.entity.Tour;
import com.etec.tourtripapi.tour.entity.TourImage;
import com.etec.tourtripapi.tour.mapper.TourImageMapper;
import com.etec.tourtripapi.tour.repository.TourImageRepository;
import com.etec.tourtripapi.tour.repository.TourRepository;
import com.etec.tourtripapi.tour.service.FileService;
import com.etec.tourtripapi.tour.service.TourImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TourImageServiceImpl implements TourImageService {

    private final TourImageRepository repository;
    private final TourRepository tourRepository;
    private final TourImageMapper mapper;
    private final FileService fileService;

    @Value("${app.images.tour-path:uploads/tours/}")
    private String tourUploadPath;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Override
    public List<ImageResponseDTO> create(ImageRequestDTO requestDTO) {
        Tour tour = tourRepository.findById(requestDTO.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with ID: " + requestDTO.getTourId()));

        List<MultipartFile> allFiles = new ArrayList<>();

        // បើផ្ញើមកតាម field "file" (១ ឯកសារ)
        if (requestDTO.getFile() != null && !requestDTO.getFile().isEmpty()) {
            allFiles.add(requestDTO.getFile());
        }

        // បើផ្ញើមកតាម field "files" (ច្រើនឯកសារ)
        if (requestDTO.getFiles() != null && !requestDTO.getFiles().isEmpty()) {
            for (MultipartFile f : requestDTO.getFiles()) {
                if (f != null && !f.isEmpty()) {
                    allFiles.add(f);
                }
            }
        }

        if (allFiles.isEmpty()) {
            throw new IllegalArgumentException("At least one image file must be provided!");
        }

        List<ImageResponseDTO> responseList = new ArrayList<>();

        for (int i = 0; i < allFiles.size(); i++) {
            MultipartFile file = allFiles.get(i);
            try {
                String fileName = fileService.uploadFile(tourUploadPath, file);
                String imageUrl = baseUrl + "/api/files/view/" + fileName;

                TourImage entity = new TourImage();
                entity.setTour(tour);
                entity.setImageUrl(imageUrl);
                // កំណត់ isPrimary សម្រាប់រូបទី ១ ប្រសិនបើមានផ្ញើមក
                entity.setIsPrimary(i == 0 && requestDTO.getIsPrimary() != null ? requestDTO.getIsPrimary() : false);
                entity.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : EntityStatus.active);

                TourImage saved = repository.save(entity);
                responseList.add(mapper.toResponseDTO(saved));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image file: " + e.getMessage());
            }
        }

        return responseList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageResponseDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponseDTO> findByTourId(Long tourId) {
        return repository.findByTourId(tourId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ImageResponseDTO update(Long id, ImageRequestDTO requestDTO) {
        TourImage existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour Image not found with id: " + id));

        Tour tour = tourRepository.findById(requestDTO.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with ID: " + requestDTO.getTourId()));

        String imageUrl = existing.getImageUrl();

        // គាំទ្រការ update ជាមួយ file ថ្មី (ប្រើ file ទី១ ប្រសិនបើមានផ្ញើមក)
        MultipartFile targetFile = (requestDTO.getFile() != null && !requestDTO.getFile().isEmpty())
                ? requestDTO.getFile()
                : (requestDTO.getFiles() != null && !requestDTO.getFiles().isEmpty() ? requestDTO.getFiles().get(0) : null);

        if (targetFile != null) {
            try {
                String fileName = fileService.uploadFile(tourUploadPath, targetFile);
                imageUrl = baseUrl + "/api/files/view/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException("Failed to update image file: " + e.getMessage());
            }
        }

        existing.setTour(tour);
        existing.setImageUrl(imageUrl);
        existing.setIsPrimary(requestDTO.getIsPrimary() != null ? requestDTO.getIsPrimary() : existing.getIsPrimary());
        if (requestDTO.getStatus() != null) {
            existing.setStatus(requestDTO.getStatus());
        }

        TourImage updated = repository.save(existing);
        return mapper.toResponseDTO(updated);
    }

    @Override
    public void updateStatus(Long id, EntityStatus status) {
        TourImage entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour Image not found with id: " + id));
        entity.setStatus(status);
        repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        TourImage entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour Image not found with id: " + id));
        repository.deleteById(id); // Soft delete
    }
}
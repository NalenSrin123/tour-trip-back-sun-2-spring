package com.etec.tourtripapi.destination.service;

import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import com.etec.tourtripapi.destination.entity.Destination;
import com.etec.tourtripapi.destination.mapper.DestinationMapper;
import com.etec.tourtripapi.destination.repository.DestinationRepository;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.tour.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationServiceImpl implements DestinationService { // <-- ត្រូវមាន implements នេះដាច់ខាត!

    private final DestinationRepository repository;
    private final DestinationMapper mapper;
    private final FileService fileService;

    @Value("${app.images.destination-path:uploads/destinations/}")
    private String destinationUploadPath;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // 1. Create a new Destination (with Single Image)
    @Override
    public DestinationResponse create(DestinationRequest.Create requestDTO) {
        Destination entity = mapper.toEntity(requestDTO);

        Object fileObj = requestDTO.getFile();
        if (fileObj instanceof MultipartFile) {
            MultipartFile multipartFile = (MultipartFile) fileObj;
            if (!multipartFile.isEmpty()) {
                try {
                    String fileName = fileService.uploadFile(destinationUploadPath, multipartFile);
                    String imageUrl = baseUrl + "/api/files/view/" + fileName;
                    entity.setImageUrl(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload destination image file: " + e.getMessage());
                }
            }
        }

        Destination saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    // 2. Find all destinations
    @Override
    @Transactional(readOnly = true)
    public List<DestinationResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // 3. Find destination by ID
    @Override
    @Transactional(readOnly = true)
    public Optional<DestinationResponse> findById(Integer id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    // 4. Update destination
    @Override
    public DestinationResponse update(Integer id, DestinationRequest.Update requestDTO) {
        Destination existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));

        mapper.updateEntityFromDto(requestDTO, existing);

        Object fileObj = requestDTO.getFile();
        if (fileObj instanceof MultipartFile) {
            MultipartFile multipartFile = (MultipartFile) fileObj;
            if (!multipartFile.isEmpty()) {
                try {
                    String fileName = fileService.uploadFile(destinationUploadPath, multipartFile);
                    String imageUrl = baseUrl + "/api/files/view/" + fileName;
                    existing.setImageUrl(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to update destination image file: " + e.getMessage());
                }
            }
        } else if (fileObj instanceof String) {
            existing.setImageUrl((String) fileObj);
        }

        Destination updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    // 5. Delete destination
    @Override
    public void deleteById(Integer id) {
        Destination existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));
        repository.deleteById(id);
    }
}
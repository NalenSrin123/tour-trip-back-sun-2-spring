package com.etec.tourtripapi.category.service;


import com.etec.tourtripapi.category.dto.request.CategoryRequestDTO;
import com.etec.tourtripapi.category.dto.response.CategoryResponseDTO;
import com.etec.tourtripapi.category.entity.Category;
import com.etec.tourtripapi.category.mapper.CategoryMapper;
import com.etec.tourtripapi.category.repository.CategoryRepository;
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
public class CategoryServiceImpl {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final FileService fileService;

    @Value("${app.images.category-path:uploads/categories/}")
    private String categoryUploadPath;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // 1. Create a new Category (with Single Image)
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
        Category entity = mapper.toEntity(requestDTO);

        // Handle single image upload
        if (requestDTO.getFile() != null && !requestDTO.getFile().isEmpty()) {
            try {
                String fileName = fileService.uploadFile(categoryUploadPath, requestDTO.getFile());
                String imageUrl = baseUrl + "/api/files/view/" + fileName;
                entity.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload category image file: " + e.getMessage());
            }
        }

        Category saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    // 2. Find all categories
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 3. Find category by ID
    @Transactional(readOnly = true)
    public Optional<CategoryResponseDTO> findById(Integer id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    // 4. Update category (updates fields and new image file if provided)
    public CategoryResponseDTO update(Integer id, CategoryRequestDTO requestDTO) {
        Category existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Update basic fields via mapper
        mapper.updateEntityFromDTO(requestDTO, existing);

        // If a new image file is provided, upload it and update imageUrl
        if (requestDTO.getFile() != null && !requestDTO.getFile().isEmpty()) {
            try {
                String fileName = fileService.uploadFile(categoryUploadPath, requestDTO.getFile());
                String imageUrl = baseUrl + "/api/files/view/" + fileName;
                existing.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update category image file: " + e.getMessage());
            }
        }

        Category updated = repository.save(existing);
        return mapper.toResponseDTO(updated);
    }

    // 5. Delete category (Soft Delete handled by Entity SQLDelete annotation)
    public void deleteById(Integer id) {
        Category existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        repository.deleteById(id);
    }
}

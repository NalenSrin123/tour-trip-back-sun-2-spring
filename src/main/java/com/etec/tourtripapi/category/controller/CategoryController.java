package com.etec.tourtripapi.category.controller;

import com.etec.tourtripapi.category.dto.request.CategoryRequestDTO;
import com.etec.tourtripapi.category.dto.response.CategoryResponseDTO;
import com.etec.tourtripapi.category.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    // 1. Create a new category (Supports multipart/form-data for file upload)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @ModelAttribute CategoryRequestDTO requestDTO) {
        CategoryResponseDTO response = categoryService.create(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // 3. Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Integer id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update category by ID (Supports updating fields and new image file)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Integer id,
            @ModelAttribute CategoryRequestDTO requestDTO) {
        CategoryResponseDTO response = categoryService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    // 5. Delete category (Soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
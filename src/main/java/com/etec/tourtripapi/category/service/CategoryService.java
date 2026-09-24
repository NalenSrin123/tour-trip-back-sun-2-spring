package com.etec.tourtripapi.category.service;

import com.etec.tourtripapi.category.dto.request.CategoryRequestDTO;
import com.etec.tourtripapi.category.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create(CategoryRequestDTO request);
    CategoryResponseDTO update(Integer id, CategoryRequestDTO request);
    CategoryResponseDTO getById(Integer id);
    List<CategoryResponseDTO> getAll();
    void delete(Integer id);
}
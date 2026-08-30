package com.etec.tourtripapi.category.service;

import com.etec.tourtripapi.category.dto.request.CategoryRequest;
import com.etec.tourtripapi.category.dto.response.CategoryResponse;
import com.etec.tourtripapi.category.entity.Category;
import com.etec.tourtripapi.category.mapper.CategoryMapper;
import com.etec.tourtripapi.category.repository.CategoryRepository;
import com.etec.tourtripapi.common.exception.DuplicateResourceException;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException(
                    "Category slug '" + request.getSlug() + "' already exists");
        }
        Category category = categoryMapper.toEntity(request);
        if (category.getStatus() == null) {
            category.setStatus(true);
        }
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Integer id, CategoryRequest request) {
        Category category = findOrThrow(id);
        if (categoryRepository.existsBySlugAndIdNot(request.getSlug(), id)) {
            throw new DuplicateResourceException(
                    "Category slug '" + request.getSlug() + "' already exists");
        }
        categoryMapper.updateEntityFromRequest(request, category);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Integer id) {
        return categoryMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.delete(findOrThrow(id));
    }

    private Category findOrThrow(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
    }
}
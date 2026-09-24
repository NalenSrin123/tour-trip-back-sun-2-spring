package com.etec.tourtripapi.category.mapper;


import com.etec.tourtripapi.category.dto.request.CategoryRequestDTO;
import com.etec.tourtripapi.category.dto.response.CategoryResponseDTO;
import com.etec.tourtripapi.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


    public Category toEntity(CategoryRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        return Category.builder()
                .name(requestDTO.getName())
                .slug(requestDTO.getSlug())
                .description(requestDTO.getDescription())
                .status(requestDTO.getStatus() != null ? requestDTO.getStatus() : true)
                .build();
    }


    public void updateEntityFromDTO(CategoryRequestDTO requestDTO, Category category) {
        if (requestDTO == null || category == null) {
            return;
        }

        if (requestDTO.getName() != null) {
            category.setName(requestDTO.getName());
        }
        if (requestDTO.getSlug() != null) {
            category.setSlug(requestDTO.getSlug());
        }
        if (requestDTO.getDescription() != null) {
            category.setDescription(requestDTO.getDescription());
        }
        if (requestDTO.getStatus() != null) {
            category.setStatus(requestDTO.getStatus());
        }
    }

    public CategoryResponseDTO toResponseDTO(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .status(category.getStatus())
                .build();
    }
}
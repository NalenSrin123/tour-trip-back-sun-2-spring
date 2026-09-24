package com.etec.tourtripapi.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    private String name;

    private String slug;

    private String description;

    private MultipartFile file;

    private Boolean status = true;
}
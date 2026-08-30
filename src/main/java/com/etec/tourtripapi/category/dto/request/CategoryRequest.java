package com.etec.tourtripapi.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "slug is required")
    @Size(max = 100)
    private String slug;

    private String description;

    @Size(max = 250)
    private String imageUrl;

    private Boolean status;
}
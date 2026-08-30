package com.etec.tourtripapi.category.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
    private Integer id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
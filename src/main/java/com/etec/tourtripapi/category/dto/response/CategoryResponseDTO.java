package com.etec.tourtripapi.category.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CategoryResponseDTO {

    private Integer id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private Boolean status;

}
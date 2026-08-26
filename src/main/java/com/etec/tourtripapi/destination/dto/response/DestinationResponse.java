package com.etec.tourtripapi.destination.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DestinationResponse {
    private Integer id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
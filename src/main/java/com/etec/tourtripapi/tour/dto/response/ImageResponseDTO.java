package com.etec.tourtripapi.tour.dto.response;

import com.etec.tourtripapi.common.enums.EntityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponseDTO {

    private Long id;
    private Long tourId;
    private String imageUrl;
    private Boolean isPrimary;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
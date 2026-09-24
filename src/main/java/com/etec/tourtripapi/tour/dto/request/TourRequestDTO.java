package com.etec.tourtripapi.tour.dto.request;

import com.etec.tourtripapi.common.enums.EntityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourRequestDTO {

    private Long id;
    private String title;
    private String slug;
    private Integer durationDay;
    private Integer durationNight;
    private BigDecimal basePrice;
    private BigDecimal priceOverride;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long categoryId;
    private String categoryName;

    private Long destinationId;
    private String destinationName;
}
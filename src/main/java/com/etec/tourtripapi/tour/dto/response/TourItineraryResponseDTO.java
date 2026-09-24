package com.etec.tourtripapi.tour.dto.response;

import com.etec.tourtripapi.common.enums.ItineraryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourItineraryResponseDTO {

    private Long id;
    private Integer dayNumber;
    private String title;
    private String description;
    private String mealsIncluded;
    private ItineraryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Expose relational identifiers or lightweight info instead of the entity
    private Long tourId;
    private String tourTitle;
}
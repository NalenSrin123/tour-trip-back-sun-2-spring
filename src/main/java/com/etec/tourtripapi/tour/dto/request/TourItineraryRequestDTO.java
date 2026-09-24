package com.etec.tourtripapi.tour.dto.request;

import com.etec.tourtripapi.common.enums.ItineraryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourItineraryRequestDTO {

    private Long id;

    @NotNull(message = "Day number is required")
    private Integer dayNumber;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String mealsIncluded;
    private ItineraryStatus status;

    @NotNull(message = "Tour ID is required")
    private Long tourId; // Expose ID instead of the full Tour entity
}
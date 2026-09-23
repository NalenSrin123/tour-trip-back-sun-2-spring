package com.etec.tourtripapi.schedule.dto.request;

import com.etec.tourtripapi.common.enums.TourScheduleStatus;
import jakarta.validation.constraints.NotNull;
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
public class TourScheduleRequest {

    @NotNull(message = "tourId is required")
    private Long tourId;

    @NotNull(message = "startDatetime is required")
    private LocalDateTime startDatetime;

    @NotNull(message = "endDatetime is required")
    private LocalDateTime endDatetime;

    private LocalDateTime bookingCutoffDatetime;
    private Integer minCapacity;
    private Integer maxCapacity;
    private Integer currentBooked;
    private BigDecimal priceOverride;
    private TourScheduleStatus status;
    private String notes;
}

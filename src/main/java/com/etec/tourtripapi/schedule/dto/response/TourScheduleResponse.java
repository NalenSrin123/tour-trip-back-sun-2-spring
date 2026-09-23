package com.etec.tourtripapi.schedule.dto.response;

import com.etec.tourtripapi.common.enums.TourScheduleStatus;
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
public class TourScheduleResponse {
    private Long id;
    private Long tourId;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private LocalDateTime bookingCutoffDatetime;
    private Integer minCapacity;
    private Integer maxCapacity;
    private Integer currentBooked;
    private Integer version;
    private BigDecimal priceOverride;
    private TourScheduleStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.etec.tourtripapi.tour.dto.response;

import com.etec.tourtripapi.common.enums.IncludedExcludedStatus;
import com.etec.tourtripapi.common.enums.InclusionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncludedExcludedResponseDTO {
    private Long Id;
    private Long tourId;
    private InclusionType Type;
    private String Description;
    private IncludedExcludedStatus Status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
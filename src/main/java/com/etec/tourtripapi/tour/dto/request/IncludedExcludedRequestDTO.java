package com.etec.tourtripapi.tour.dto.request;

import com.etec.tourtripapi.common.enums.IncludedExcludedStatus;
import com.etec.tourtripapi.common.enums.InclusionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class IncludedExcludedRequestDTO {

    @NotNull(message = "Tour ID is required")
    private Long tourId;

    @NotNull(message = "Type is required (included or excluded)")
    private InclusionType Type;

    @NotBlank(message = "Description is required")
    private String Description;

    private IncludedExcludedStatus Status; // Optional, can default to active in entity
}

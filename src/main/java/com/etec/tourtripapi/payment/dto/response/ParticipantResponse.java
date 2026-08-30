package com.etec.tourtripapi.payment.dto.response;

import com.etec.tourtripapi.common.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {
    private Long id;
    private String ageGroup;
    private String name;
    private Sex sex;
}

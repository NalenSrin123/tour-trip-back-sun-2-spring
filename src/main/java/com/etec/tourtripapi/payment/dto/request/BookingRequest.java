package com.etec.tourtripapi.payment.dto.request;

import com.etec.tourtripapi.common.enums.BookingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long userId;
    private Long tourScheduleId;
    private String specialRequests;
    private BookingType bookingType;
    private List<ParticipantRequest>  participantRequests;
}

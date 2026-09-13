package com.etec.tourtripapi.payment.dto.response;

import com.etec.tourtripapi.common.enums.BookingStatus;
import com.etec.tourtripapi.common.enums.BookingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long tourScheduleId;
    private BigDecimal totalPrice;
    private String specialRequests;
    private BookingStatus bookingStatus;
    private BookingType bookingType;
    private Integer memberCount;
    private List<ParticipantResponse> participants;
}

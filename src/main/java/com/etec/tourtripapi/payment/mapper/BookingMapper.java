package com.etec.tourtripapi.payment.mapper;

import com.etec.tourtripapi.payment.dto.request.BookingRequest;
import com.etec.tourtripapi.payment.dto.response.BookingResponse;
import com.etec.tourtripapi.payment.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ParticipantMapper.class})
public interface BookingMapper {
    @Mapping(target = "participants", source = "participantRequests")
    Booking toEntity(BookingRequest request);
    BookingResponse toResponse(Booking entity);
}

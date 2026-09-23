package com.etec.tourtripapi.payment.mapper;

import com.etec.tourtripapi.payment.dto.request.ParticipantRequest;
import com.etec.tourtripapi.payment.dto.response.ParticipantResponse;
import com.etec.tourtripapi.payment.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipantMapper {
    Participant toEntity(ParticipantRequest request);
    ParticipantResponse toResponse(Participant entity);

    void updateEntityFromRequest(ParticipantRequest request, @MappingTarget Participant entity);
}

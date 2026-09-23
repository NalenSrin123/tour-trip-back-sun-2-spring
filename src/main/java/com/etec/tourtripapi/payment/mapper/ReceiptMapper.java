package com.etec.tourtripapi.payment.mapper;

import com.etec.tourtripapi.payment.dto.request.ReceiptRequest;
import com.etec.tourtripapi.payment.dto.response.ReceiptResponse;
import com.etec.tourtripapi.payment.entity.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReceiptMapper {
    @Mapping(target = "tourTitle", source = "tourTittle")
    Receipt toEntity(ReceiptRequest request);

    ReceiptResponse toResponse(Receipt entity);

    @Mapping(target = "tourTitle", source = "tourTittle")
    void updateEntityFromRequest(ReceiptRequest request, @MappingTarget Receipt entity);
}

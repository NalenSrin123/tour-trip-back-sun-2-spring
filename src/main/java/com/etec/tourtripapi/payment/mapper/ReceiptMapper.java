package com.etec.tourtripapi.payment.mapper;

import com.etec.tourtripapi.payment.dto.response.ReceiptResponse;
import com.etec.tourtripapi.payment.entity.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReceiptMapper {
    ReceiptResponse toResponse(Receipt entity);
}

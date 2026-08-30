package com.etec.tourtripapi.payment.mapper;

import com.etec.tourtripapi.payment.dto.request.PaymentRequest;
import com.etec.tourtripapi.payment.dto.response.PaymentResponse;
import com.etec.tourtripapi.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ReceiptMapper.class})
public interface PaymentMapper {
    Payment toEntity(PaymentRequest request);
    PaymentResponse toResponse(Payment entity);
}

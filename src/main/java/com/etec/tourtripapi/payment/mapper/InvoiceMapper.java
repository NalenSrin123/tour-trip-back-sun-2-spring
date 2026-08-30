package com.etec.tourtripapi.payment.mapper;

import com.etec.tourtripapi.payment.dto.request.InvoiceRequest;
import com.etec.tourtripapi.payment.dto.response.InvoiceResponse;
import com.etec.tourtripapi.payment.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PaymentMapper.class})
public interface InvoiceMapper {
    Invoice toEntity(InvoiceRequest request);
    InvoiceResponse toResponse(Invoice entity);
}

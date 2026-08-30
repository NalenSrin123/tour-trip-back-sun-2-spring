package com.etec.tourtripapi.payment.service;

import com.etec.tourtripapi.payment.dto.request.InvoiceRequest;
import com.etec.tourtripapi.payment.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);
    InvoiceResponse getInvoiceById(Long id);
    List<InvoiceResponse> getAllInvoices();
    void deleteInvoice(Long id);
}

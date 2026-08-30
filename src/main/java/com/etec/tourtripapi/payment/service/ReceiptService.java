package com.etec.tourtripapi.payment.service;

import com.etec.tourtripapi.payment.dto.request.ReceiptRequest;
import com.etec.tourtripapi.payment.dto.response.ReceiptResponse;

import java.util.List;

public interface ReceiptService {
    ReceiptResponse createReceipt(ReceiptRequest request);
    ReceiptResponse getReceiptById(Long id);
    List<ReceiptResponse> getAllReceipts();
    void deleteReceipt(Long id);
}

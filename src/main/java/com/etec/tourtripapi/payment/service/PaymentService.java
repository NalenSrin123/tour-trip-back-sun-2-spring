package com.etec.tourtripapi.payment.service;

import com.etec.tourtripapi.payment.dto.request.PaymentRequest;
import com.etec.tourtripapi.payment.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long id);
    List<PaymentResponse> getAllPayments();
    void deletePayment(Long id);
}

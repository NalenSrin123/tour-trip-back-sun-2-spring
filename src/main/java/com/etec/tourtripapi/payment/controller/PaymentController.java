package com.etec.tourtripapi.payment.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.payment.dto.request.PaymentRequest;
import com.etec.tourtripapi.payment.dto.response.PaymentResponse;
import com.etec.tourtripapi.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Payment created successfully", 201, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Payment retrieved successfully", 200, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> response = paymentService.getAllPayments();
        return ResponseEntity.ok(
                new ApiResponse<>("Payments retrieved successfully", 200, response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Payment deleted successfully", 200, null)
        );
    }
}

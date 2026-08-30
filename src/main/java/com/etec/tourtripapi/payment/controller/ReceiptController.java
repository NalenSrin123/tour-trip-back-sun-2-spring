package com.etec.tourtripapi.payment.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.payment.dto.request.ReceiptRequest;
import com.etec.tourtripapi.payment.dto.response.ReceiptResponse;
import com.etec.tourtripapi.payment.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReceiptResponse>> createReceipt(@RequestBody ReceiptRequest request) {
        ReceiptResponse response = receiptService.createReceipt(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Receipt created successfully", 201, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReceiptResponse>> getReceiptById(@PathVariable Long id) {
        ReceiptResponse response = receiptService.getReceiptById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Receipt retrieved successfully", 200, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReceiptResponse>>> getAllReceipts() {
        List<ReceiptResponse> response = receiptService.getAllReceipts();
        return ResponseEntity.ok(
                new ApiResponse<>("Receipts retrieved successfully", 200, response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReceipt(@PathVariable Long id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Receipt deleted successfully", 200, null)
        );
    }
}

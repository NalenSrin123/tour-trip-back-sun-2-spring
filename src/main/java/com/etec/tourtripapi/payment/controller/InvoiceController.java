package com.etec.tourtripapi.payment.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.payment.dto.request.InvoiceRequest;
import com.etec.tourtripapi.payment.dto.response.InvoiceResponse;
import com.etec.tourtripapi.payment.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@RequestBody InvoiceRequest request) {
        InvoiceResponse response = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Invoice created successfully", 201, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable Long id) {
        InvoiceResponse response = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Invoice retrieved successfully", 200, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAllInvoices() {
        List<InvoiceResponse> response = invoiceService.getAllInvoices();
        return ResponseEntity.ok(
                new ApiResponse<>("Invoices retrieved successfully", 200, response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Invoice deleted successfully", 200, null)
        );
    }
}

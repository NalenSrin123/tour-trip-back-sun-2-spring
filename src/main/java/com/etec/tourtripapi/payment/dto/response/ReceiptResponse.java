package com.etec.tourtripapi.payment.dto.response;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptResponse {
    private Long id;
    private String receiptNo;
    private LocalDateTime issuedAt;
    private String tourTitle;
    private LocalDateTime tourDate;
    private Integer numTravelers;
    private BigDecimal subTotal;
    private BigDecimal taxAmount;
    private BigDecimal totalPaid;
    private PaymentMethod paymentMethod;
    private String pdfUrl;
}

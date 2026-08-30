package com.etec.tourtripapi.payment.dto.request;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long invoiceId;
    private BigDecimal amount;
    private PaymentMethod  paymentMethod;
    private Long TransactionId;
}

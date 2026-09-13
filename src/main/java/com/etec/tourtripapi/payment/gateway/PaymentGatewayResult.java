package com.etec.tourtripapi.payment.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentGatewayResult {
    private boolean success;
    private String transactionId;
    private String message;
}

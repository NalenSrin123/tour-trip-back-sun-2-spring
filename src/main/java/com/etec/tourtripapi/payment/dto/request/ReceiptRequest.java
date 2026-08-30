package com.etec.tourtripapi.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptRequest {
    private String receiptNo;
    private String tourTittle;
}

package com.etec.tourtripapi.payment.gateway;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import com.etec.tourtripapi.common.utils.CodeGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankTransferPaymentGateway implements PaymentGateway {

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.bank_transfer;
    }

    @Override
    public PaymentGatewayResult process(BigDecimal amount) {
        return PaymentGatewayResult.builder()
                .success(true)
                .transactionId(CodeGenerator.generate("BANK"))
                .message("Bank transfer recorded, pending bank confirmation")
                .build();
    }
}

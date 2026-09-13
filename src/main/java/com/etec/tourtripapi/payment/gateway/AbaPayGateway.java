package com.etec.tourtripapi.payment.gateway;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import com.etec.tourtripapi.common.utils.CodeGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AbaPayGateway implements PaymentGateway {

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.aba_pay;
    }

    @Override
    public PaymentGatewayResult process(BigDecimal amount) {
        return PaymentGatewayResult.builder()
                .success(true)
                .transactionId(CodeGenerator.generate("ABA"))
                .message("ABA Pay payment processed successfully")
                .build();
    }
}

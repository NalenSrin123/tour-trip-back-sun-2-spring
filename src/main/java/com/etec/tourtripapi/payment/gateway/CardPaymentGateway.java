package com.etec.tourtripapi.payment.gateway;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import com.etec.tourtripapi.common.utils.CodeGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardPaymentGateway implements PaymentGateway {

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.card;
    }

    @Override
    public PaymentGatewayResult process(BigDecimal amount) {
        return PaymentGatewayResult.builder()
                .success(true)
                .transactionId(CodeGenerator.generate("CARD"))
                .message("Card payment processed successfully")
                .build();
    }
}

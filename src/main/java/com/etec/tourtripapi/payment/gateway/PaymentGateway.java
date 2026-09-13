package com.etec.tourtripapi.payment.gateway;

import com.etec.tourtripapi.common.enums.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentGateway {

    PaymentMethod getSupportedMethod();

    PaymentGatewayResult process(BigDecimal amount);
}

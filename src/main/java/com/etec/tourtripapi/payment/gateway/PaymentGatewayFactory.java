package com.etec.tourtripapi.payment.gateway;

import com.etec.tourtripapi.common.enums.PaymentMethod;
import com.etec.tourtripapi.common.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentGatewayFactory {

    private final Map<PaymentMethod, PaymentGateway> gatewaysByMethod;

    public PaymentGatewayFactory(List<PaymentGateway> gateways) {
        this.gatewaysByMethod = gateways.stream()
                .collect(Collectors.toMap(PaymentGateway::getSupportedMethod, Function.identity()));
    }

    public PaymentGateway getGateway(PaymentMethod method) {
        PaymentGateway gateway = gatewaysByMethod.get(method);
        if (gateway == null) {
            throw new BadRequestException("Unsupported payment method: " + method);
        }
        return gateway;
    }
}

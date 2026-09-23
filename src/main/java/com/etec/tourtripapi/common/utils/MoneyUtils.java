package com.etec.tourtripapi.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private static final int SCALE = 2;

    private MoneyUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal round(BigDecimal value) {
        return (value == null ? BigDecimal.ZERO : value).setScale(SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTax(BigDecimal subTotal, BigDecimal taxRate) {
        if (subTotal == null || taxRate == null) {
            return round(BigDecimal.ZERO);
        }
        return round(subTotal.multiply(taxRate));
    }

    public static BigDecimal sum(BigDecimal... values) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return round(total);
    }
}

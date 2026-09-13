package com.etec.tourtripapi.common.utils;

import java.util.UUID;

public final class CodeGenerator {

    private CodeGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String generate(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

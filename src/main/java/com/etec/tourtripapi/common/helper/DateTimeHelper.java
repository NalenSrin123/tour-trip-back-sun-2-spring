package com.etec.tourtripapi.common.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeHelper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(FORMATTER);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}

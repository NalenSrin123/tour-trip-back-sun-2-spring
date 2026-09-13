package com.etec.tourtripapi.common.validator;

import com.etec.tourtripapi.common.constants.AppConstants;
import com.etec.tourtripapi.common.exception.BadRequestException;

public final class ValidationUtils {
    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static void requireText(String value, String fieldName) {
        if (isBlank(value)) {
            throw new BadRequestException(fieldName + " is required");
        }
    }

    public static void validatePage(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number must be greater than or equal to 0");
        }
        if (size <= 0 || size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must be between 1 and " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}

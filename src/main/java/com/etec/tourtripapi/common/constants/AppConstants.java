package com.etec.tourtripapi.common.constants;

public final class AppConstants {
    public static final String API_VERSION = "/api/v1";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final int MAX_PAGE_SIZE = 100;
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private AppConstants() {
        throw new IllegalStateException("Utility class");
    }
}

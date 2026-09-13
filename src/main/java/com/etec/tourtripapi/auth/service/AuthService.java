package com.etec.tourtripapi.auth.service;

import com.etec.tourtripapi.auth.dto.response.AuthUserResponse;
import com.etec.tourtripapi.auth.dto.request.LoginRequest;
import com.etec.tourtripapi.auth.dto.request.RegisterRequest;
import com.etec.tourtripapi.auth.dto.request.VerifyOtpRequest;

public interface AuthService {
    AuthUserResponse register(RegisterRequest request);
    void verifyOtp(VerifyOtpRequest request);
    void login(LoginRequest request);
}
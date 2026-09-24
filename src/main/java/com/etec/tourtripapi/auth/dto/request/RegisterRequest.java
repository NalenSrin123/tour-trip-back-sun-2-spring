package com.etec.tourtripapi.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password_hash;

    @NotBlank(message = "Password confirmation is required")
    private String password_hash_confirmation;

    @NotBlank(message = "Channel is required (email or telegram)")
    private String channel;

    // Both optional — user can provide one, both, or neither
    private String phone;
    private String telegramChatId;
}
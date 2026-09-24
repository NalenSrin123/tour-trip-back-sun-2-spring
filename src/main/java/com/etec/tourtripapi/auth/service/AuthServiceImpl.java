package com.etec.tourtripapi.auth.service;

import com.etec.tourtripapi.auth.dto.request.LoginRequest;
import com.etec.tourtripapi.auth.dto.request.RegisterRequest;
import com.etec.tourtripapi.auth.dto.request.VerifyOtpRequest;
import com.etec.tourtripapi.auth.dto.response.AuthUserResponse;
import com.etec.tourtripapi.common.enums.UserStatus;
import com.etec.tourtripapi.common.exception.DuplicateResourceException;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.user.entity.User;
import com.etec.tourtripapi.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final EmailService emailService;
    private final TelegramService telegramService;

    @Override
public AuthUserResponse register(RegisterRequest request) {
    // 1. Check email not already taken
    boolean emailExists = userRepository.existsByEmail(request.getEmail());

    if (emailExists) {
        throw new DuplicateResourceException(
            "Email '" + request.getEmail() + "' is already registered");
    }

        // 2. Check password confirmation matches
        if (!request.getPassword_hash().equals(request.getPassword_hash_confirmation())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // 3. Validate channel vs provided fields
        validateChannel(request);

        // 4. Create user — phone and telegramChatId are both optional
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordEncoder.encode(request.getPassword_hash()))
                .status(UserStatus.INACTIVE)
                .telegramChatId(request.getTelegramChatId())
                .build();
        userRepository.save(user);

        // 5. Generate OTP and send via resolved channel
        String rawOtp = otpService.generateAndSave(user);
        String resolvedChannel = resolveChannel(request);
        sendOtp(user, rawOtp, resolvedChannel);

        return AuthUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public void verifyOtp(VerifyOtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean valid = otpService.verify(user, request.getCode());
        if (!valid) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        user.setStatus(UserStatus.ACTIVE);
        user.setEmailVerifiedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword_hash(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new IllegalArgumentException(
                "Account not verified. Please verify your OTP first.");
        }

        // Auto-detect channel for login OTP
        String channel = user.getTelegramChatId() != null ? "telegram" : "email";
        String rawOtp = otpService.generateAndSave(user);
        sendOtp(user, rawOtp, channel);
    }

    // ── Resolve actual delivery channel ───────────────────────────────────────
    /**
     * Determines the actual OTP delivery method based on what the user provided.
     * - channel=telegram + telegramChatId → telegram
     * - channel=telegram + no telegramChatId + phone → fallback to email
     * - channel=email → email
     * - channel=phone → email (SMS not yet implemented, phone stored for future)
     */
    private String resolveChannel(RegisterRequest request) {
        String channel = request.getChannel().toLowerCase();

        return switch (channel) {
            case "telegram" -> {
                if (request.getTelegramChatId() != null
                        && !request.getTelegramChatId().isBlank()) {
                    yield "telegram";
                }
                // Has phone but no telegramChatId → fallback to email
                yield "email";
            }
            case "phone" -> "email"; // SMS not yet implemented → fallback to email
            default -> "email";
        };
    }

    // ── Validation ────────────────────────────────────────────────────────────
    private void validateChannel(RegisterRequest request) {
        String channel = request.getChannel().toLowerCase();

        switch (channel) {
            case "telegram" -> {
                // telegramChatId OR phone must be provided
                boolean hasTelegram = request.getTelegramChatId() != null
                        && !request.getTelegramChatId().isBlank();
                boolean hasPhone = request.getPhone() != null
                        && !request.getPhone().isBlank();
                if (!hasTelegram && !hasPhone) {
                    throw new IllegalArgumentException(
                        "Please provide either telegramChatId or phone number " +
                        "when using channel 'telegram'.");
                }
            }
            case "email" -> {
                // email always available — no extra fields needed
            }
            case "phone" -> {
                // phone must be provided
                if (request.getPhone() == null || request.getPhone().isBlank()) {
                    throw new IllegalArgumentException(
                        "Phone number is required when channel is 'phone'.");
                }
            }
            default -> throw new IllegalArgumentException(
                "Invalid channel '" + request.getChannel() +
                "'. Valid options: 'email', 'telegram', 'phone'.");
        }
    }

    // ── OTP Delivery ──────────────────────────────────────────────────────────
    private void sendOtp(User user, String rawOtp, String channel) {
        switch (channel.toLowerCase()) {
            case "telegram" -> telegramService.sendOtp(
                user.getTelegramChatId(),
                user.getName(),
                user.getEmail(),
                rawOtp);
            default -> emailService.sendOtpEmail(
                user.getEmail(),
                user.getName(),
                rawOtp);
        }
    }
}
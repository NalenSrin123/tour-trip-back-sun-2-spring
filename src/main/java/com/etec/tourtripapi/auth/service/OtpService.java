package com.etec.tourtripapi.auth.service;

import com.etec.tourtripapi.auth.entity.Otp;
import com.etec.tourtripapi.auth.repository.OtpRepository;
import com.etec.tourtripapi.user.entity.User;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${otp.expiry.minutes}")
    private int otpExpiryMinutes;

    private final SecureRandom random = new SecureRandom();

    public String generateAndSave(User user) {
        // Generate 6-digit OTP
        String rawOtp = String.format("%06d", random.nextInt(1000000));

        // Hash it before storing
        String hashedOtp = passwordEncoder.encode(rawOtp);

        Otp otp = Otp.builder()
                .user(user)
                .code(hashedOtp)
                .expiresAt(LocalDateTime.now().plusMinutes(otpExpiryMinutes))
                .isUsed(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        otpRepository.save(otp);
        return rawOtp; // return raw OTP to send to user
    }

    public boolean verify(User user, String rawCode) {
        Otp otp = otpRepository
                .findTopByUserIdAndIsUsedFalseOrderByCreatedAtDesc(user.getId())
                .orElse(null);

        if (otp == null) return false;
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) return false;
        if (!passwordEncoder.matches(rawCode, otp.getCode())) return false;

        // Mark as used
        otp.setIsUsed(true);
        otp.setExpiredAt(LocalDateTime.now());
        otp.setUpdatedAt(LocalDateTime.now());
        otpRepository.save(otp);

        return true;
    }
}
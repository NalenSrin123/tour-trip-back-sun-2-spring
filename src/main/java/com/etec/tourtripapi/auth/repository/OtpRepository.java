package com.etec.tourtripapi.auth.repository;

import com.etec.tourtripapi.auth.entity.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findTopByUserIdAndIsUsedFalseOrderByCreatedAtDesc(Integer userId);
}
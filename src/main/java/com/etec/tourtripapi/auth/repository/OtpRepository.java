package com.etec.tourtripapi.auth.repository;

import com.etec.tourtripapi.auth.entity.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findTopByUserIdAndIsUsedFalseOrderByCreatedAtDesc(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM otps WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(Integer userId);
}
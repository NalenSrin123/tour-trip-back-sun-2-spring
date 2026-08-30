package com.etec.tourtripapi.user.repository;

import com.etec.tourtripapi.user.entity.SocialAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer> {
    List<SocialAccount> findByUserId(Integer userId);
    Optional<SocialAccount> findByProviderAndProviderId(String provider, String providerId);
}
package com.etec.tourtripapi.common.idempotency.repository;

import com.etec.tourtripapi.common.idempotency.entity.IdempotencyKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey, String> {
    Optional<IdempotencyKey> findByIdempotencyKey(String idempotencyKey);
}

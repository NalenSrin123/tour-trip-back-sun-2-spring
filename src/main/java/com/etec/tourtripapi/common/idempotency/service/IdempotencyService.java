package com.etec.tourtripapi.common.idempotency.service;

import com.etec.tourtripapi.common.idempotency.entity.IdempotencyKey;
import com.etec.tourtripapi.common.idempotency.repository.IdempotencyKeyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyKeyRepository repository;
    private final ObjectMapper objectMapper;

    /**
     * Runs the given action exactly once per idempotencyKey.
     * If the key was already processed, returns the cached response instead.
     */
    public <T> T execute(String idempotencyKey, String requestPath,
                          Class<T> responseType, Supplier<T> action) {

        Optional<IdempotencyKey> existing = repository.findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            IdempotencyKey record = existing.get();
            if ("COMPLETED".equals(record.getStatus())) {
                return deserialize(record.getResponseBody(), responseType);
            }
            throw new IllegalStateException("Request with this idempotency key is already being processed");
        }

        // Reserve the key first, so a concurrent duplicate request sees it immediately
        IdempotencyKey newRecord = IdempotencyKey.builder()
                .idempotencyKey(idempotencyKey)
                .requestPath(requestPath)
                .status("PROCESSING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repository.save(newRecord);

        // Run the actual business logic (e.g. process payment)
        T result = action.get();

        // Cache the result and mark completed
        newRecord.setStatus("COMPLETED");
        newRecord.setResponseBody(serialize(result));
        newRecord.setUpdatedAt(LocalDateTime.now());
        repository.save(newRecord);

        return result;
    }

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize idempotent response", e);
        }
    }

    private <T> T deserialize(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize cached idempotent response", e);
        }
    }
}

package com.etec.tourtripapi.common.idempotency.service;

import com.etec.tourtripapi.common.idempotency.entity.IdempotencyKey;
import com.etec.tourtripapi.common.idempotency.repository.IdempotencyKeyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyKeyRepository repository;
    private final ObjectMapper objectMapper;

    private static final long EXPIRY_HOURS = 24;

    /**
     * Runs the given action exactly once per idempotencyKey + request body combination.
     * - Same key + same body, already completed  -> returns the cached response
     * - Same key + different body                -> rejected (payload mismatch)
     * - Same key, still processing (race)         -> rejected (in-flight)
     * - Expired key                                -> treated as a fresh request
     */
    public <T> T execute(String idempotencyKey, String requestPath,
                          Object requestBody, Class<T> responseType, Supplier<T> action) {

        String bodyHash = hash(requestBody);
        Optional<IdempotencyKey> existing = repository.findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            IdempotencyKey record = existing.get();

            if (record.getExpiresAt().isBefore(LocalDateTime.now())) {
                repository.delete(record);
            } else if (!record.getRequestBodyHash().equals(bodyHash)) {
                throw new IllegalArgumentException(
                    "Idempotency key reused with a different request payload");
            } else if ("COMPLETED".equals(record.getStatus())) {
                return deserialize(record.getResponseBody(), responseType);
            } else {
                throw new IllegalStateException(
                    "Request with this idempotency key is already being processed");
            }
        }

        IdempotencyKey newRecord = IdempotencyKey.builder()
                .idempotencyKey(idempotencyKey)
                .requestPath(requestPath)
                .requestBodyHash(bodyHash)
                .status("PROCESSING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(EXPIRY_HOURS))
                .build();
        repository.save(newRecord);

        T result = action.get();

        newRecord.setStatus("COMPLETED");
        newRecord.setStatusCode(200);
        newRecord.setResponseBody(serialize(result));
        newRecord.setUpdatedAt(LocalDateTime.now());
        repository.save(newRecord);

        return result;
    }

    private String hash(Object body) {
        try {
            String json = objectMapper.writeValueAsString(body);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(json.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash request body for idempotency check", e);
        }
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
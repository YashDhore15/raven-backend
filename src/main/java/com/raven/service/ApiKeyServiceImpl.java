package com.raven.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raven.dto.ApiKeyCreationResult;
import com.raven.dto.ApiKeyResponse;
import com.raven.entity.ApiKey;
import com.raven.entity.Tenant;
import com.raven.exception.ApiKeyResourceNotFoundException;
import com.raven.repository.ApiKeyRepository;
import com.raven.repository.TenantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private static final int API_KEY_RANDOM_BYTES = 32;
    private static final String API_KEY_PREFIX = "rvn_";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final TenantRepository tenantRepository;
    private final ApiKeyRepository apiKeyRepository;

    @Override
    @Transactional
    public ApiKeyCreationResult createApiKey(Long tenantId, String name) {
        validateTenantId(tenantId);
        String normalizedName = validateAndNormalizeName(name);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ApiKeyResourceNotFoundException(
                        "Tenant not found with id " + tenantId));

        String rawApiKey = generateRawApiKey();
        LocalDateTime now = LocalDateTime.now();

        ApiKey apiKey = ApiKey.builder()
                .tenant(tenant)
                .name(normalizedName)
                .keyHash(hashApiKey(rawApiKey))
                .createdAt(now)
                .updatedAt(now)
                .build();

        ApiKey savedApiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreationResult(
                savedApiKey.getId(),
                savedApiKey.getName(),
                rawApiKey,
                savedApiKey.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiKeyResponse> getApiKeys(Long tenantId) {
        validateTenantId(tenantId);

        return apiKeyRepository.findByTenantId(tenantId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void revokeApiKey(Long tenantId, Long apiKeyId) {
        validateTenantId(tenantId);

        if (apiKeyId == null) {
            throw new IllegalArgumentException("API key id is required");
        }

        ApiKey apiKey = apiKeyRepository
                .findByIdAndTenantId(apiKeyId, tenantId)
                .orElseThrow(() -> new ApiKeyResourceNotFoundException(
                        "API key not found for this tenant"));

        if (apiKey.getRevokedAt() == null) {
            LocalDateTime now = LocalDateTime.now();
            apiKey.setRevokedAt(now);
            apiKey.setUpdatedAt(now);
            apiKeyRepository.save(apiKey);
        }
    }

    private String generateRawApiKey() {
        byte[] randomBytes = new byte[API_KEY_RANDOM_BYTES];
        SECURE_RANDOM.nextBytes(randomBytes);

        return API_KEY_PREFIX
                + Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(randomBytes);
    }

    private String hashApiKey(String rawApiKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    rawApiKey.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "SHA-256 is unavailable", exception);
        }
    }

    private ApiKeyResponse toResponse(ApiKey apiKey) {
        return new ApiKeyResponse(
                apiKey.getId(),
                apiKey.getName(),
                apiKey.getCreatedAt(),
                apiKey.getUpdatedAt(),
                apiKey.getRevokedAt());
    }

    private void validateTenantId(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant id is required");
        }
    }

    private String validateAndNormalizeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("API key name is required");
        }

        String normalizedName = name.trim();

        if (normalizedName.length() > 255) {
            throw new IllegalArgumentException(
                    "API key name must not exceed 255 characters");
        }

        return normalizedName;
    }
}


/*
 * `ApiKeyServiceImpl` contains the ApiKey business logic:

- `@Service`: Registers the class with Spring.
- `@RequiredArgsConstructor`: Injects both repositories through a generated constructor.
- `@Transactional`: Keeps database operations safe and consistent.
- `createApiKey`: Validates the tenant and name, generates a secure raw key, hashes it, stores only the hash, and returns the raw key once.
- `getApiKeys`: Returns key metadata without exposing the raw key or hash.
- `revokeApiKey`: Confirms tenant ownership and sets `revokedAt` instead of deleting the row.
- `SecureRandom`: Generates an unpredictable 256-bit key.
- `SHA-256`: Converts the raw key into the hash stored in the database.
- Helper methods handle validation, generation, hashing, and DTO conversion.
 */
 
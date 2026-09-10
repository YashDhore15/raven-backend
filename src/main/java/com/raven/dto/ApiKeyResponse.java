package com.raven.dto;

import java.time.LocalDateTime;

public record ApiKeyResponse(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime revokedAt
) {
}
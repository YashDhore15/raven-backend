package com.raven.dto;

import java.time.LocalDateTime;

public record ApiKeyCreationResult(
        Long id,
        String name,
        String rawApiKey,
        LocalDateTime createdAt
) {
}
package com.raven.dto;

import java.time.LocalDateTime;

public record TenantResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
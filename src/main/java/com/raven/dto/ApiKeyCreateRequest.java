package com.raven.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApiKeyCreateRequest(

        @NotBlank(message = "API key name is required")
        @Size(max = 255, message = "API key name must not exceed 255 characters")
        String name

) {
}
package com.raven.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderUpdateRequest {

    @NotBlank(message = "Provider name is required")
    private String name;

    @NotBlank(message = "Provider channel is required")
    private String channel;

    @NotNull(message = "Provider active status is required")
    private Boolean isActive;
}
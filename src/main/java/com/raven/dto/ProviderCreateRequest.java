package com.raven.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderCreateRequest {

    @NotBlank(message = "Provider name is required")
    private String name;

    @NotBlank(message = "Provider code is required")
    private String code;

    @NotBlank(message = "Provider channel is required")
    private String channel;

    private Boolean isActive;
}
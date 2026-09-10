package com.raven.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProviderResponse {

    private Long id;

    private String name;

    private String code;

    private String channel;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
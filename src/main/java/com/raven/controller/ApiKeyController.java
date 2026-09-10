package com.raven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.raven.dto.ApiKeyCreateRequest;
import com.raven.dto.ApiKeyCreationResult;
import com.raven.dto.ApiKeyResponse;
import com.raven.service.ApiKeyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private static final String TENANT_ID_HEADER = "X-Tenant-Id";

    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyCreationResult> createApiKey(
            @RequestHeader(TENANT_ID_HEADER) Long tenantId,
            @Valid @RequestBody ApiKeyCreateRequest request) {

        ApiKeyCreationResult result =
                apiKeyService.createApiKey(tenantId, request.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public List<ApiKeyResponse> getApiKeys(
            @RequestHeader(TENANT_ID_HEADER) Long tenantId) {

        return apiKeyService.getApiKeys(tenantId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revokeApiKey(
            @RequestHeader(TENANT_ID_HEADER) Long tenantId,
            @PathVariable Long id) {

        apiKeyService.revokeApiKey(tenantId, id);
        return ResponseEntity.noContent().build();
    }
}
package com.raven.service;

import java.util.List;

import com.raven.dto.ApiKeyCreationResult;
import com.raven.dto.ApiKeyResponse;

public interface ApiKeyService {

    ApiKeyCreationResult createApiKey(Long tenantId, String name);

    List<ApiKeyResponse> getApiKeys(Long tenantId);

    void revokeApiKey(Long tenantId, Long apiKeyId);
}
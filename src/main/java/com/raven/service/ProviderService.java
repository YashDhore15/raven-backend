package com.raven.service;

import java.util.List;

import com.raven.dto.ProviderCreateRequest;
import com.raven.dto.ProviderResponse;
import com.raven.dto.ProviderUpdateRequest;

public interface ProviderService {

    ProviderResponse createProvider(ProviderCreateRequest request);

    List<ProviderResponse> getAllProviders();

    ProviderResponse getProviderById(Long id);

    ProviderResponse getProviderByCode(String code);

    ProviderResponse updateProvider(Long id, ProviderUpdateRequest request);

    void deactivateProvider(Long id);
}
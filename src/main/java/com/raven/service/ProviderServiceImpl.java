package com.raven.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.raven.dto.ProviderCreateRequest;
import com.raven.dto.ProviderResponse;
import com.raven.dto.ProviderUpdateRequest;
import com.raven.entity.Provider;
import com.raven.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public ProviderResponse createProvider(ProviderCreateRequest request) {

        // Reject duplicate provider code
        if (providerRepository.findByCode(request.getCode()).isPresent()) {
            throw new IllegalArgumentException(
                    "Provider code already exists: " + request.getCode());
        }

        LocalDateTime now = LocalDateTime.now();

        Provider provider = new Provider();

        provider.setName(request.getName());
        provider.setCode(request.getCode());
        provider.setChannel(request.getChannel());

        // isActive is optional during creation.
        // If omitted, create the provider as active.
        provider.setActive(
                request.getIsActive() == null
                        ? true
                        : request.getIsActive());

        provider.setCreatedAt(now);
        provider.setUpdatedAt(now);

        Provider savedProvider = providerRepository.save(provider);

        return toResponse(savedProvider);
    }

    @Override
    public List<ProviderResponse> getAllProviders() {

        return providerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProviderResponse getProviderById(Long id) {

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Provider not found with id: " + id));

        return toResponse(provider);
    }

    @Override
    public ProviderResponse getProviderByCode(String code) {

        Provider provider = providerRepository.findByCode(code)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Provider not found with code: " + code));

        return toResponse(provider);
    }

    @Override
    public ProviderResponse updateProvider(
            Long id,
            ProviderUpdateRequest request) {

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Provider not found with id: " + id));

        provider.setName(request.getName());
        provider.setChannel(request.getChannel());
        provider.setActive(request.getIsActive());
        provider.setUpdatedAt(LocalDateTime.now());

        Provider updatedProvider = providerRepository.save(provider);

        return toResponse(updatedProvider);
    }

    @Override
    public void deactivateProvider(Long id) {

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Provider not found with id: " + id));

        provider.setActive(false);
        provider.setUpdatedAt(LocalDateTime.now());

        providerRepository.save(provider);
    }

    private ProviderResponse toResponse(Provider provider) {

        return ProviderResponse.builder()
                .id(provider.getId())
                .name(provider.getName())
                .code(provider.getCode())
                .channel(provider.getChannel())
                .isActive(provider.isActive())
                .createdAt(provider.getCreatedAt())
                .updatedAt(provider.getUpdatedAt())
                .build();
    }
}
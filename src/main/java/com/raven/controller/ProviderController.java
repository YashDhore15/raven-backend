package com.raven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raven.dto.ProviderCreateRequest;
import com.raven.dto.ProviderResponse;
import com.raven.dto.ProviderUpdateRequest;
import com.raven.service.ProviderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(
            @Valid @RequestBody ProviderCreateRequest request) {

        ProviderResponse response =
                providerService.createProvider(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponse>> getAllProviders() {

        return ResponseEntity.ok(
                providerService.getAllProviders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponse> getProviderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                providerService.getProviderById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ProviderResponse> getProviderByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                providerService.getProviderByCode(code));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> updateProvider(
            @PathVariable Long id,
            @Valid @RequestBody ProviderUpdateRequest request) {

        return ResponseEntity.ok(
                providerService.updateProvider(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateProvider(
            @PathVariable Long id) {

        providerService.deactivateProvider(id);

        return ResponseEntity.noContent().build();
    }
}
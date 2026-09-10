package com.raven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raven.entity.ApiKey;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<ApiKey> findByTenantId(Long tenantId);

    Optional<ApiKey> findByKeyHash(String keyHash);

    List<ApiKey> findByTenantIdAndRevokedAtIsNull(Long tenantId);

    Optional<ApiKey> findByIdAndTenantId(Long id, Long tenantId);
}
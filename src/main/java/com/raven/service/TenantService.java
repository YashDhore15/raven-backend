package com.raven.service;

import java.util.List;

import com.raven.dto.TenantUpdateRequest;
import com.raven.entity.Tenant;

public interface TenantService {

	Tenant createTenant(Tenant tenant, String password);

    Tenant getTenantById(Long id);

    List<Tenant> getAllTenants();

    Tenant updateTenant(Long id, TenantUpdateRequest tenant);

    void deleteTenant(Long id);
}
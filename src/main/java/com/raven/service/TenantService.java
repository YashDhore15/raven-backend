package com.raven.service;

import com.raven.entity.Tenant;

import java.util.List;

public interface TenantService {

	Tenant createTenant(Tenant tenant, String password);

    Tenant getTenantById(Long id);

    List<Tenant> getAllTenants();

    Tenant updateTenant(Long id, Tenant tenant);

    void deleteTenant(Long id);
}
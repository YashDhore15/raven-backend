package com.raven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.raven.dto.TenantCreateRequest;
import com.raven.entity.Tenant;
import com.raven.service.TenantService;

import jakarta.validation.Valid;

@RestController
public class TenantController {
	
	private final TenantService tenantService;
	
	@Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

//	@PostMapping("/api/v1/tenants")
//	public Tenant createTenant(@RequestBody Tenant tenant) {
//		
//		tenant.setCreatedAt(java.time.LocalDateTime.now());
//		tenant.setUpdatedAt(java.time.LocalDateTime.now());
//		
//	    return tenantService.createTenant(tenant);
//	}
	
	@PostMapping("/api/v1/tenants")
	public Tenant createTenant(@Valid @RequestBody TenantCreateRequest request) {

	    Tenant tenant = new Tenant();

	    tenant.setName(request.getName());
	    tenant.setEmail(request.getEmail());
	    tenant.setPasswordHash(request.getPassword());

	    return tenantService.createTenant(tenant);
	}
}




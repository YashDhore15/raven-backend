package com.raven.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raven.dto.TenantCreateRequest;
import com.raven.dto.TenantResponse;
import com.raven.dto.TenantUpdateRequest;
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
	
	private TenantResponse toResponse(Tenant tenant) {

	    return new TenantResponse(
	            tenant.getId(),
	            tenant.getName(),
	            tenant.getEmail(),
	            tenant.getCreatedAt(),
	            tenant.getUpdatedAt()
	    );
	}
	
	@PostMapping("/api/v1/tenants")
	public TenantResponse createTenant(@Valid @RequestBody TenantCreateRequest request) {

	    Tenant tenant = new Tenant();

	    tenant.setName(request.name());
	    tenant.setEmail(request.email());

	    Tenant savedTenant = tenantService.createTenant(tenant, request.password());
	    
	    return toResponse(savedTenant);
	}
	
	@GetMapping("/api/v1/tenants/{id}")
	public TenantResponse getTenantById(@PathVariable Long id) {

	    Tenant tenant = tenantService.getTenantById(id);

	    return toResponse(tenant);
	}
	
	@GetMapping("/api/v1/tenants")
	public List<TenantResponse> getAllTenants() {

	    return tenantService.getAllTenants()
	            .stream()
	            .map(this::toResponse)
	            .toList();
	}
	
	@PutMapping("/api/v1/tenants/{id}")
	public TenantResponse updateTenant(
				@PathVariable Long id,
				@Valid @RequestBody TenantUpdateRequest tenant 
			) {
		
		return toResponse(tenantService.updateTenant(id, tenant));
	}
	
	@DeleteMapping("/api/v1/tenants/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTenant(@PathVariable Long id) {

	    tenantService.deleteTenant(id);
	}
}











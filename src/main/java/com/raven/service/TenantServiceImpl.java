package com.raven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raven.dto.TenantUpdateRequest;
import com.raven.entity.Tenant;
import com.raven.exception.EmailAlreadyExistsException;
import com.raven.exception.TenantNotFoundException;
import com.raven.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public TenantServiceImpl(
            TenantRepository tenantRepository,
            PasswordEncoder passwordEncoder) {

        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public Tenant createTenant(Tenant tenant) {
//        return tenantRepository.save(tenant);
//    }
    
    @Override
    public Tenant createTenant(Tenant tenant, String password) {
    	
    	if (tenantRepository.existsByEmail(tenant.getEmail())) {
    		throw new EmailAlreadyExistsException("Email is already registered");
        }

        String hashedPassword = passwordEncoder.encode(password);

        tenant.setPasswordHash(hashedPassword);

        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant getTenantById(Long id) {
        return tenantRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Tenant updateTenant(Long id, TenantUpdateRequest tenant) {
        Tenant existingTenant = getTenantById(id);

        existingTenant.setName(tenant.name());
        existingTenant.setEmail(tenant.email());

        return tenantRepository.save(existingTenant);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant existingTenant = getTenantById(id);

        existingTenant.setDeletedAt(java.time.LocalDateTime.now());

        tenantRepository.save(existingTenant);
    }
}
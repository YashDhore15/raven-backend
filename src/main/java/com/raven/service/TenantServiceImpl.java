package com.raven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raven.entity.Tenant;
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

        String hashedPassword = passwordEncoder.encode(password);

        tenant.setPasswordHash(hashedPassword);

        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant updateTenant(Long id, Tenant tenant) {
        Tenant existingTenant = getTenantById(id);

        existingTenant.setName(tenant.getName());
        existingTenant.setEmail(tenant.getEmail());

        return tenantRepository.save(existingTenant);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant existingTenant = getTenantById(id);

        existingTenant.setDeletedAt(java.time.LocalDateTime.now());

        tenantRepository.save(existingTenant);
    }
}
package com.raven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raven.entity.Tenant;
import com.raven.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantRepository tenantRepository;

	@Override
	public Tenant createTenant(Tenant tenant) {
		
		return tenantRepository.save(tenant);
	}

	@Override
	public Tenant getTenantById(Long id) {
		
		return null;
	}

	@Override
	public List<Tenant> getAllTenants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tenant updateTenant(Long id, Tenant tenant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTenant(Long id) {
		// TODO Auto-generated method stub
		
	}

}

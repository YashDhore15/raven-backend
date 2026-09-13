package com.raven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raven.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
	
	boolean existsByEmail(String email);
	
	Optional<Tenant> findByIdAndDeletedAtIsNull(Long id);

	List<Tenant> findAllByDeletedAtIsNull();
}

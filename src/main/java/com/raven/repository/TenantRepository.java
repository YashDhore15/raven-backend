package com.raven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raven.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}

package com.etec.tourtripapi.role.repository;

import com.etec.tourtripapi.role.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByName(String name);
}
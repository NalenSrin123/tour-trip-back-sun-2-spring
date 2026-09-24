package com.etec.tourtripapi.role.repository;

import com.etec.tourtripapi.role.entity.UserPermission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Integer> {
    List<UserPermission> findByUserId(Integer userId);
}
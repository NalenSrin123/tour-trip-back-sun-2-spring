package com.etec.tourtripapi.role.repository;

import com.etec.tourtripapi.role.entity.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUserId(Integer userId);
    boolean existsByUserIdAndRoleId(Integer userId, Integer roleId);
    void deleteByUserIdAndRoleId(Integer userId, Integer roleId);
}
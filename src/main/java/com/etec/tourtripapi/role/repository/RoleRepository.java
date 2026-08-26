package com.etec.tourtripapi.role.repository;

import com.etec.tourtripapi.role.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByUserId(Integer userId);
}
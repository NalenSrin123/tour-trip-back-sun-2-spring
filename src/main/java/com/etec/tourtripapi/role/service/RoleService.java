package com.etec.tourtripapi.role.service;

import com.etec.tourtripapi.role.dto.RoleRequest;
import com.etec.tourtripapi.role.dto.RoleResponse;
import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    RoleResponse update(Integer id, RoleRequest request);
    RoleResponse getById(Integer id);
    List<RoleResponse> getByUserId(Integer userId);
    void delete(Integer id);
}
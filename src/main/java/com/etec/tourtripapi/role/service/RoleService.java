package com.etec.tourtripapi.role.service;

import com.etec.tourtripapi.role.dto.AssignRoleRequest;
import com.etec.tourtripapi.role.dto.RoleRequest;
import com.etec.tourtripapi.role.dto.RoleResponse;
import com.etec.tourtripapi.role.dto.UserRoleResponse;
import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    RoleResponse update(Integer id, RoleRequest request);
    RoleResponse getById(Integer id);
    List<RoleResponse> getAll();
    void delete(Integer id);

    UserRoleResponse assignRoleToUser(AssignRoleRequest request);
    void removeRoleFromUser(AssignRoleRequest request);
    List<UserRoleResponse> getRolesByUserId(Integer userId);
}
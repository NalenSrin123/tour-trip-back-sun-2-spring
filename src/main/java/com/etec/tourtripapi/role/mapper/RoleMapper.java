package com.etec.tourtripapi.role.mapper;

import com.etec.tourtripapi.role.dto.RoleResponse;
import com.etec.tourtripapi.role.dto.UserRoleResponse;
import com.etec.tourtripapi.role.entity.Role;
import com.etec.tourtripapi.role.entity.UserRole;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roles);

    @Mapping(target = "userId",   source = "user.id")
    @Mapping(target = "roleId",   source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "roleType", source = "role.type")
    UserRoleResponse toUserRoleResponse(UserRole userRole);

    List<UserRoleResponse> toUserRoleResponseList(List<UserRole> userRoles);
}
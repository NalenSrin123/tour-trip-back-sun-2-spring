package com.etec.tourtripapi.role.mapper;

import com.etec.tourtripapi.role.dto.RoleResponse;
import com.etec.tourtripapi.role.entity.Role;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "userId", source = "user.id")
    RoleResponse toResponse(Role role);

    List<RoleResponse> toResponseList(List<Role> roles);
}
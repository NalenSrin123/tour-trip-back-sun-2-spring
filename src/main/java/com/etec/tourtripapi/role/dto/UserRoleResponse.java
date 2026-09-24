package com.etec.tourtripapi.role.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoleResponse {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private String roleName;
    private String roleType;
}
package com.etec.tourtripapi.role.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignRoleRequest {

    @NotNull(message = "userId is required")
    private Integer userId;

    @NotNull(message = "roleId is required")
    private Integer roleId;
}
package com.etec.tourtripapi.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequest {

    @NotNull(message = "userId is required")
    private Integer userId;

    @NotBlank(message = "name is required")
    private String name;

    private String type;
}
package com.etec.tourtripapi.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {

    @NotBlank(message = "name is required")
    private String name;

    private String type;
}
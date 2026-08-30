package com.etec.tourtripapi.role.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleResponse {
    private Integer id;
    private String name;
    private String type;
}
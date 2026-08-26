package com.etec.tourtripapi.role.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleResponse {
    private Integer id;
    private Integer userId;
    private String name;
    private String type;
}
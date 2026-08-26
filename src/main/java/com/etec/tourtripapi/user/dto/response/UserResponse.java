package com.etec.tourtripapi.user.dto.response;

import com.etec.tourtripapi.common.enums.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private UserStatus status;
    private LocalDateTime createdAt;
}
package com.etec.tourtripapi.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialAccountResponse {
    private Integer id;
    private Integer userId;
    private String provider;
    private String providerId;
}
package com.etec.tourtripapi.user.mapper;

import com.etec.tourtripapi.user.dto.response.SocialAccountResponse;
import com.etec.tourtripapi.user.entity.SocialAccount;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocialAccountMapper {

    @Mapping(target = "userId", source = "user.id")
    SocialAccountResponse toResponse(SocialAccount socialAccount);

    List<SocialAccountResponse> toResponseList(List<SocialAccount> socialAccounts);
}
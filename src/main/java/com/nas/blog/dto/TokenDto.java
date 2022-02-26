package com.nas.blog.dto;

import com.nas.blog.config.jwt.JwtHeaderUtilEnums;
import lombok.Builder;
import lombok.Data;

@Data
public class TokenDto {
    private String grantType;
    private String accessToken;

    @Builder
    public TokenDto(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }

    public static TokenDto of(String accessToken){
        return TokenDto.builder()
                .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
                .accessToken(accessToken)
                .build();
    }
}

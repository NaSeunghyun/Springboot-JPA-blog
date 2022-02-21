package com.nas.blog.config.oauth2;

import lombok.RequiredArgsConstructor;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {

    private final Map<String, Object> attributes;
    private final String provider;
    private final Map<String, Object> kakaoAccount;

    public KakaoUserInfo(Map<String, Object> attributes, String provider) {
        this.attributes = attributes;
        this.provider = provider;
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getUserEmail() {
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getUserName() {
        return provider + "_" + getUserProviderId();
    }

    @Override
    public String getUserProvider() {
        return provider;
    }

    @Override
    public String getUserProviderId() {
        return (String) attributes.get("id").toString();
    }
}

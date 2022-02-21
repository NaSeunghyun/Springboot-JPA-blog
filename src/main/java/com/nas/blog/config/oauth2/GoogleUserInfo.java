package com.nas.blog.config.oauth2;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements Oauth2UserInfo {

    private final Map<String, Object> attributes;
    private final String provider;

    @Override
    public String getUserEmail() {
        return (String) attributes.get("email");
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
        return (String) attributes.get("sub");
    }
}

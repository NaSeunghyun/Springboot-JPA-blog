package com.nas.blog.config.oauth2;

import java.util.Map;

public class OAuthAttributes {

    private Oauth2UserInfo oauth2UserInfo;

    public OAuthAttributes(Oauth2UserInfo oauth2UserInfo) {
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static Oauth2UserInfo of(String provider, Map<String, Object> attributes){
        if (provider.equals("facebook")) {
            return ofFacebook(provider, attributes);
        }
        if (provider.equals("kakao")){
            return ofKakao(provider, attributes);
        }
        return ofGoogle(provider, attributes);
    }

    public static Oauth2UserInfo ofGoogle(String provider, Map<String, Object> attributes){
        return new GoogleUserInfo(attributes, provider);
    }

    public static Oauth2UserInfo ofFacebook(String provider, Map<String, Object> attributes){
        return new FacebookUserInfo(attributes, provider);
    }

    public static Oauth2UserInfo ofKakao(String provider, Map<String, Object> attributes){
        return new KakaoUserInfo(attributes, provider);
    }
}

package com.nas.blog.config.oauth2;

public interface Oauth2UserInfo {
    String getUserEmail();
    String getUserName();
    String getUserProvider();
    String getUserProviderId();

}

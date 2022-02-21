package com.nas.blog.config.oauth2;

import com.nas.blog.config.auth.PrincipalDetail;
import com.nas.blog.entity.User;
import com.nas.blog.user.model.RoleType;
import com.nas.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        Oauth2UserInfo oauth2UserInfo = OAuthAttributes.of(provider, oAuth2User.getAttributes());

        User user = joinNonmember(oauth2UserInfo, provider);

        return new PrincipalDetail(user, oAuth2User.getAttributes());
    }

    private User joinNonmember(Oauth2UserInfo oauth2UserInfo, String provider) {

        String username = oauth2UserInfo.getUserName();
        String email = oauth2UserInfo.getUserEmail();
        String providerId = oauth2UserInfo.getUserProviderId();

        return userRepository.findByEmail(oauth2UserInfo.getUserEmail()).orElseGet(
                () -> userRepository.save(User.builder()
                        .userName(username)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .email(email)
                        .role(RoleType.USER)
                        .provider(provider)
                        .providerId(providerId)
                        .build())
        );
    }
}

package com.example.travel.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KakaoAuthService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 카카오 사용자 정보 처리
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
        String email = (String) kakaoAccount.get("email");

        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttribute("properties");
        String nickname = (String) properties.get("nickname");


        // 사용자 등록 로직 추가

        return oAuth2User;
    }
}

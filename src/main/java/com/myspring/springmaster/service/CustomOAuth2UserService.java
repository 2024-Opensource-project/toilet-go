package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.entity.User;
import com.myspring.springmaster.dataAccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;
import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;
    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository,  HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;

    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 소셜 제공자에서 반환된 사용자 정보 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> mutableAttributes = new HashMap<>(attributes);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 소셜 제공자 이름 (Google, Naver, Kakao)
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // 이메일 값이 없는 경우 기본 이메일 설정
        if (email == null || email.isEmpty()) {
            email = registrationId + "_user_" + UUID.randomUUID() + "@toilet.com";
            mutableAttributes.put("email", email);
        }
        // 이름 값이 없는 경우 기본 이름 설정
        if (name == null || name.isEmpty()) {
            name = "Anonymous User";
            mutableAttributes.put("name", name);
        }


        String userId = email.length() > 16 ? email.substring(0,16) : email;


        // 사용자 정보 데이터베이스에 저장 (없는 경우에만 저장)
        String finalEmail = email;
        String finalName = name;
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // 새 사용자 저장
                    User newUser = User.builder()
                            .userId(userId) // 소셜 로그인에서는 이메일을 userId로 사용
                            .email(finalEmail)
                            .name(finalName)
                            .phoneNumber("N/A")
                            .password("") // 소셜 로그인 사용자는 비밀번호 필요 없음
                            .registrationId(registrationId)
                            .roleId(1) // 기본 사용자 역할 설정 (e.g., USER)
                            .build();
                    return userRepository.save(newUser);
                });

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("isLoggedIn", true);
        httpSession.setAttribute("userName", user.getName());

        // OAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                mutableAttributes,
                "email"); // OAuth2User 기본 식별자로 email 사용
    }
}

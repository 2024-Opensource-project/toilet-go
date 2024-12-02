package com.myspring.springmaster.config;

import com.myspring.springmaster.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (필요 시 활성화)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**", "/error", "/favicon.ico", "/", "/signin", "/signup", "/guest-login", "/find-id", "/find-password", "/toilet/**", "/reviews/toilet/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/signin") // 로그인 페이지 경로
                        .loginProcessingUrl("/signin") // 로그인 폼 제출 경로
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 페이지
                        .failureUrl("/signin?error=true") // 로그인 실패 시 이동할 페이지
                        .permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/signin") // 소셜 로그인 페이지
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)) // 사용자 정보 처리 서비스
                        .defaultSuccessUrl("/") // 소셜 로그인 성공 후 이동할 경로
                )
                .logout(logout -> logout
                        .logoutUrl("/signout") // 로그아웃 경로
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 이동 경로
                        .invalidateHttpSession(true) // 세션 무효화
                );

        return http.build();
    }

}

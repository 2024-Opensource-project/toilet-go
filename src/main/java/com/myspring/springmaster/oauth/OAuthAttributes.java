package com.myspring.springmaster.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final String name;
    private final String email;

    @Builder
    public OAuthAttributes(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }
}

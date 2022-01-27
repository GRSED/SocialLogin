package com.example.socialLogin.service;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "provider")
public class ProviderConfig {
    List<SocialLoginService> list;
}

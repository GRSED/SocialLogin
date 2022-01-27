package com.example.socialLogin.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "provider")
public class ProviderConfig {
    Map<String, SocialLoginService> map;
}

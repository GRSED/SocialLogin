package com.example.socialLogin;

import lombok.Getter;

@Getter
public class PlatformConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestAccessTokenUri;
    private String requestProfileApiUri;
}

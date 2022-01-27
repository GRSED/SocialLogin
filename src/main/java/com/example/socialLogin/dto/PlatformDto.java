package com.example.socialLogin.dto;

import lombok.Setter;

@Setter
public class PlatformDto {
    public String type;
    public String clientId;
    public String clientSecret;
    public String redirectUri;
    public String requestAccessTokenUri;
    public String requestProfileApiUri;
}

package com.example.socialLogin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Platform {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestAccessTokenUri;
    private String requestProfileApiUri;
}

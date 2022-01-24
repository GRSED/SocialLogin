package com.example.socialLogin;

import lombok.Getter;

import static com.example.socialLogin.Constant.*;

@Getter
public enum SocialLoginPlatform {

    NAVER(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, NAVER_REDIRECT_URI, NAVER_REQUEST_ACCESS_TOKEN_URI, NAVER_REQUEST_PROFILE_API_URI),
    KAKAO(KAKAO_CLIENT_ID, KAKAO_CLIENT_SECRET, KAKAO_REDIRECT_URI, KAKAO_REQUEST_ACCESS_TOKEN_URI, KAKAO_REQUEST_PROFILE_API_URI),
    GOOGLE(GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, GOOGLE_REDIRECT_URI, GOOGLE_REQUEST_ACCESS_TOKEN_URI, GOOGLE_REQUEST_PROFILE_API_URI);

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestAccessTokenUri;
    private String requestProfileApiUri;

    SocialLoginPlatform(String clientId, String clientSecret, String redirectUri, String requestAccessTokenUri, String requestProfileApiUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.requestAccessTokenUri = requestAccessTokenUri;
        this.requestProfileApiUri = requestProfileApiUri;
    }
}

package com.example.socialLogin.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface SocialLoginInterface {
    HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code);

    ResponseEntity<String> requestAccessToken(HttpEntity request);

    HttpEntity<MultiValueMap<String, String>> requiredForRequestApi(String accessToken);

    ResponseEntity<String> requestApi(HttpEntity request);

    String requestProfile(String code);
}

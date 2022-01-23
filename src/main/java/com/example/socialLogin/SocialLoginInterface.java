package com.example.socialLogin;

import com.example.socialLogin.dto.Platform;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface SocialLoginInterface {

    HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code, Platform platform);

    ResponseEntity<String> requestAccessToken(HttpEntity request, Platform platform);

    HttpEntity<MultiValueMap<String, String>> requiredForRequestApi(String accessToken);

    ResponseEntity<String> requestApi(HttpEntity request, Platform platform);
}

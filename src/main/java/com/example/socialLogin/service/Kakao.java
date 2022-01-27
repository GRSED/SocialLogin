package com.example.socialLogin.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@ConfigurationProperties(prefix = "kakao")
public class Kakao extends SocialLoginInterface {
    @Override
    public HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        return new HttpEntity<>(params, headers);
    }

    @Override
    public ResponseEntity<String> requestAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                requestAccessTokenUri,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> requiredForRequestApi(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    @Override
    public ResponseEntity<String> requestApi(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                requestProfileApiUri,
                HttpMethod.POST,
                request,
                String.class
        );
    }
}

package com.example.socialLogin.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.socialLogin.Constant.*;

@Service()
public class NaverSocialLoginService {

    public HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("code", code);
        return new HttpEntity<>(params, headers);
    }

    public ResponseEntity<String> requestAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                NAVER_REQUEST_ACCESS_TOKEN_URI,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public HttpEntity<MultiValueMap<String, String>> requiredForRequestApi(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    public ResponseEntity<String> requestApi(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                NAVER_REQUEST_PROFILE_API_URI,
                HttpMethod.POST,
                request,
                String.class
        );
    }

}

package com.example.socialLogin.service;

import com.example.socialLogin.SocialLoginInterface;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.socialLogin.Constant.GOOGLE_CLIENT_ID;
import static com.example.socialLogin.Constant.GOOGLE_CLIENT_SECRET;

@Service
public class GoogleSocialLoginService implements SocialLoginInterface {
    @Override
    public HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/google");
        params.add("code", code);
        return new HttpEntity<>(params, headers);
    }

    @Override
    public ResponseEntity<String> requestAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> requiredForRequestApi(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(headers);
    }

    @Override
    public ResponseEntity<String> requestApi(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                request,
                String.class
        );
    }

    public String requestProfile(String code) {
        JSONObject jsonObject = new JSONObject(requestAccessToken(requiredForRequestAccessToken(code)).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestApi(requiredForRequestApi(accessToken)).getBody();
    }
}

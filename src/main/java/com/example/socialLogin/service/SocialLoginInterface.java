package com.example.socialLogin.service;

import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Setter
public abstract class SocialLoginInterface {
    public String clientId;
    public String clientSecret;
    public String redirectUri;
    public String requestAccessTokenUri;
    public String requestProfileApiUri;

    abstract HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code);

    abstract ResponseEntity<String> requestAccessToken(HttpEntity request);

    abstract HttpEntity<MultiValueMap<String, String>> requiredForRequestApi(String accessToken);

    abstract ResponseEntity<String> requestApi(HttpEntity request);

    public String requestProfile(String code) {
        JSONObject jsonObject = new JSONObject(requestAccessToken(requiredForRequestAccessToken(code)).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestApi(requiredForRequestApi(accessToken)).getBody();
    }
}

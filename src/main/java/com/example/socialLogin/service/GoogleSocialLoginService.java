package com.example.socialLogin.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.socialLogin.Constant.*;

@Service
public class GoogleSocialLoginService implements SocialLoginInterface {
    @Value("${google.client.id}")
    String clientId;

    @Value("${google.client.secret}")
    String clientSecret;

    @Value("${google.redirect.uri}")
    String redirectUri;

    @Value("${google.request.access.token.uri}")
    String requestAccessTokenUri;

    @Value("${google.request.profile.api.uri}")
    String requestProfileApiUri;

    @Override
    public HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded");

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
        return new HttpEntity<>(headers);
    }

    @Override
    public ResponseEntity<String> requestApi(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                requestProfileApiUri,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    @Override
    public String requestProfile(String code) {
        JSONObject jsonObject = new JSONObject(requestAccessToken(requiredForRequestAccessToken(code)).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestApi(requiredForRequestApi(accessToken)).getBody();
    }
}

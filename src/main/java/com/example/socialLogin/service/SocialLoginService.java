package com.example.socialLogin.service;

import com.example.socialLogin.dto.PlatformDto;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialLoginService extends PlatformDto {

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

    public ResponseEntity<String> requestAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                requestAccessTokenUri,
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
        if (type.equals("google")) {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    requestProfileApiUri,
                    HttpMethod.GET,
                    request,
                    String.class
            );
        } else {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    requestProfileApiUri,
                    HttpMethod.POST,
                    request,
                    String.class
            );
        }
    }

    public String requestProfile(String code) {
        JSONObject jsonObject = new JSONObject(requestAccessToken(requiredForRequestAccessToken(code)).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestApi(requiredForRequestApi(accessToken)).getBody();
    }
}

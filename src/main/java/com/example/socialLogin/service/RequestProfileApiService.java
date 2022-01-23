package com.example.socialLogin.service;

import com.example.socialLogin.SocialLoginInterface;
import com.example.socialLogin.dto.Platform;
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
public class RequestProfileApiService implements SocialLoginInterface {
    @Override
    public HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code, Platform platform) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", platform.getClientId());
        params.add("client_secret", platform.getClientSecret());
        params.add("redirect_uri", platform.getRedirectUri());
        params.add("code", code);
        return new HttpEntity<>(params, headers);
    }

    @Override
    public ResponseEntity<String> requestAccessToken(HttpEntity request, Platform platform) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                platform.getRequestAccessTokenUri(),
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
    public ResponseEntity<String> requestApi(HttpEntity request, Platform platform) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                platform.getRequestProfileApiUri(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public String requestProfile(String code, Platform platform) {
        JSONObject jsonObject = new JSONObject(requestAccessToken(requiredForRequestAccessToken(code, platform), platform).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestApi(requiredForRequestApi(accessToken), platform).getBody();
    }
}

package com.example.socialLogin.service;

import com.example.socialLogin.SocialLoginPlatform;
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
public class RequestProfileApiService {

    public HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessToken(String code, SocialLoginPlatform platform) {
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

    public ResponseEntity<String> requestAccessToken(HttpEntity request, SocialLoginPlatform platform) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                platform.getRequestAccessTokenUri(),
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

    public ResponseEntity<String> requestApi(HttpEntity request, SocialLoginPlatform platform) {
        RestTemplate restTemplate = new RestTemplate();
        if (platform.name().equals("GOOGLE")) {
            return restTemplate.exchange(
                    platform.getRequestProfileApiUri(),
                    HttpMethod.GET,
                    request,
                    String.class
            );
        }
        return restTemplate.exchange(
                platform.getRequestProfileApiUri(),
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public String requestProfile(String code, SocialLoginPlatform platform) {
        JSONObject jsonObject = new JSONObject(requestAccessToken(requiredForRequestAccessToken(code, platform), platform).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestApi(requiredForRequestApi(accessToken), platform).getBody();
    }
}

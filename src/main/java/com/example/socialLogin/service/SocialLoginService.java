package com.example.socialLogin.service;

import com.example.socialLogin.PlatformConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialLoginService extends SocialLoginInterface {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestAccessTokenUri;
    private String requestProfileApiUri;

    public SocialLoginService(PlatformConfig platformConfig) {
        this.clientId = platformConfig.getClientId();
        this.clientSecret = platformConfig.getClientSecret();
        this.redirectUri = platformConfig.getRedirectUri();
        this.requestAccessTokenUri = platformConfig.getRequestAccessTokenUri();
        this.requestProfileApiUri = platformConfig.getRequestProfileApiUri();
    }

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

}

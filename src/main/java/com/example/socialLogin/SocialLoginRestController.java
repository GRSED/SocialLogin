package com.example.socialLogin;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

import static com.example.socialLogin.Constant.*;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
            JSONObject jsonObject = new JSONObject(requestAccessTokenNaver(requiredForRequestAccessTokenNaver(code)).getBody());
            String accessToken = jsonObject.getString("access_token");
            return requestProfileNaver(generateProfileRequestNaver(accessToken)).getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> requiredForRequestAccessTokenNaver(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("code", code);
        return new HttpEntity<>(params, headers);
    }

    private ResponseEntity<String> requestAccessTokenNaver(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequestNaver(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> requestProfileNaver(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                request,
                String.class);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        JSONObject jsonObject = new JSONObject(requestAccessTokenKakao(generateAuthCodeRequestKakao(code)).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestProfileKakao(generateProfileRequestKakao(accessToken)).getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequestKakao(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("client_secret", KAKAO_CLIENT_SECRET);
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
        params.add("code", code);
        return new HttpEntity<>(params, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequestKakao(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> requestAccessTokenKakao(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    private ResponseEntity<String> requestProfileKakao(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        JSONObject jsonObject = new JSONObject(requestAccessTokenGoogle(generateAuthCodeRequestGoogle(code)).getBody());
        String accessToken = jsonObject.getString("access_token");
        return requestProfileGoogle(generateProfileRequestGoogle(accessToken)).getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequestGoogle(String code) {
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

    private HttpEntity<MultiValueMap<String, String>> generateProfileRequestGoogle(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> requestAccessTokenGoogle(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    private ResponseEntity<String> requestProfileGoogle(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                request,
                String.class
        );
    }
}

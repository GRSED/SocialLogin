package com.example.socialLogin.controller;

import com.example.socialLogin.dto.Platform;
import com.example.socialLogin.service.RequestProfileApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.example.socialLogin.Constant.*;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {
    @Autowired
    RequestProfileApiService requestProfileApiService;

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        Platform naver = new Platform(NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, NAVER_REDIRECT_URI, NAVER_REQUEST_ACCESS_TOKEN_URI, NAVER_REQUEST_PROFILE_API_URI);
        if (!session.getAttribute("storedState").equals(state)) return "";
        return requestProfileApiService.requestProfile(code, naver);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        Platform kakao = new Platform(KAKAO_CLIENT_ID, KAKAO_CLIENT_SECRET, KAKAO_REDIRECT_URI, KAKAO_REQUEST_ACCESS_TOKEN_URI, KAKAO_REQUEST_PROFILE_API_URI);
        if (!session.getAttribute("storedState").equals(state)) return "";
        return requestProfileApiService.requestProfile(code, kakao);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        Platform google = new Platform(GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, GOOGLE_REDIRECT_URI, GOOGLE_REQUEST_ACCESS_TOKEN_URI, GOOGLE_REQUEST_PROFILE_API_URI);
        if (!session.getAttribute("storedState").equals(state)) return "";
        return requestProfileApiService.requestProfile(code, google);
    }
}

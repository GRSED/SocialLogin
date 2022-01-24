package com.example.socialLogin.controller;

import com.example.socialLogin.SocialLoginPlatform;
import com.example.socialLogin.service.RequestProfileApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {
    @Autowired
    RequestProfileApiService requestProfileApiService;

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return requestProfileApiService.requestProfile(code, SocialLoginPlatform.NAVER);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return requestProfileApiService.requestProfile(code, SocialLoginPlatform.KAKAO);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return requestProfileApiService.requestProfile(code, SocialLoginPlatform.GOOGLE);
    }
}

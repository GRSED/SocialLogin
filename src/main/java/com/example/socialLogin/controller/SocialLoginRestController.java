package com.example.socialLogin.controller;

import com.example.socialLogin.service.GoogleSocialLoginService;
import com.example.socialLogin.service.KakaoSocialLoginService;
import com.example.socialLogin.service.NaverSocialLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {

    @Autowired
    private NaverSocialLoginService naver;
    @Autowired
    private KakaoSocialLoginService kakao;
    @Autowired
    private GoogleSocialLoginService google;

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return naver.requestProfile(code);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return kakao.requestProfile(code);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return google.requestProfile(code);
    }
}

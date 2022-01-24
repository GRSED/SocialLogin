package com.example.socialLogin.controller;

import com.example.socialLogin.service.SocialLoginInterface;
import com.example.socialLogin.service.GoogleSocialLoginService;
import com.example.socialLogin.service.KakaoSocialLoginService;
import com.example.socialLogin.service.NaverSocialLoginService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        SocialLoginInterface naver = new NaverSocialLoginService();
        return naver.requestProfile(code);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        SocialLoginInterface kakao = new KakaoSocialLoginService();
        return kakao.requestProfile(code);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        SocialLoginInterface google = new GoogleSocialLoginService();
        return google.requestProfile(code);
    }
}

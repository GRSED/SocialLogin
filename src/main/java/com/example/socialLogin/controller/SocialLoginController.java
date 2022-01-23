package com.example.socialLogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;

import static com.example.socialLogin.Constant.*;

@Controller
public class SocialLoginController {

    @GetMapping(value = "naverLoginUrl")
    public String naverLoginUrl(HttpSession session) {
        String state = generateState();
        session.setAttribute("storedState", state);
        return "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + NAVER_REDIRECT_URI + "&state=" + state;
    }

    @GetMapping(value = "kakaoLoginUrl")
    public String kakaoLoginUrl(HttpSession session) {
        String state = generateState();
        session.setAttribute("storedState", state);
        return "redirect:https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URI + "&state=" + state;
    }

    @GetMapping(value = "googleLoginUrl")
    public String googleLoginUrl(HttpSession session) {
        String state = generateState();
        session.setAttribute("storedState", state);
        return "redirect:https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=" + GOOGLE_CLIENT_ID
                + "&redirect_uri=" + GOOGLE_REDIRECT_URI + "&scope=email profile&state=" + state;
    }

    @GetMapping(value = "naverlogin")
    public String naverLogin() {
        return "naverlogin";
    }

    @GetMapping(value = "naver2")
    public String naverProfile() {
        return "callback";
    }

    @GetMapping(value = "kakaologin")
    public String kakaoLogin() {
        return "kakaologin";
    }

    public String generateState()
    {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}

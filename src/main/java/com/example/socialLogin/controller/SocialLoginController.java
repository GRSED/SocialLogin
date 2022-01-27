package com.example.socialLogin.controller;

import com.example.socialLogin.ProviderConfig;
import com.example.socialLogin.dto.PlatformDto;
import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Controller
public class SocialLoginController {
    @Autowired
    ProviderConfig providerConfig;

    @GetMapping(value = "naverLoginUrl")
    public String naverLoginUrl(HttpSession session) {
        PlatformDto naver = providerConfig.getMap().get("naver");
        String state = generateState();
        session.setAttribute("storedState", state);
        return "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + naver.clientId
                + "&redirect_uri=" + URLEncoder.QUERY.encode(naver.redirectUri, StandardCharsets.UTF_8) + "&state=" + state;
    }

    @GetMapping(value = "kakaoLoginUrl")
    public String kakaoLoginUrl(HttpSession session) {
        PlatformDto kakao = providerConfig.getMap().get("kakao");
        String state = generateState();
        session.setAttribute("storedState", state);
        return "redirect:https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakao.clientId
                + "&redirect_uri=" + URLEncoder.QUERY.encode(kakao.redirectUri, StandardCharsets.UTF_8) + "&state=" + state;
    }

    @GetMapping(value = "googleLoginUrl")
    public String googleLoginUrl(HttpSession session) {
        PlatformDto google = providerConfig.getMap().get("google");
        String state = generateState();
        session.setAttribute("storedState", state);
        return "redirect:https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=" + google.clientId
                + "&redirect_uri=" + URLEncoder.QUERY.encode(google.redirectUri, StandardCharsets.UTF_8) + "&scope=email profile&state=" + state;
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

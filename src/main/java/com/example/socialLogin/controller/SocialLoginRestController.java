package com.example.socialLogin.controller;

import com.example.socialLogin.SocialLoginPlatform;
import com.example.socialLogin.service.SocialLoginInterface;
import com.example.socialLogin.service.GoogleSocialLoginService;
import com.example.socialLogin.service.KakaoSocialLoginService;
import com.example.socialLogin.service.NaverSocialLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {
    @Autowired
    private List<SocialLoginInterface> platformList;

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return platformList.get(SocialLoginPlatform.NAVER.ordinal()).requestProfile(code);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return platformList.get(SocialLoginPlatform.KAKAO.ordinal()).requestProfile(code);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return platformList.get(SocialLoginPlatform.GOOGLE.ordinal()).requestProfile(code);
    }
}

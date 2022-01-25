package com.example.socialLogin.controller;

import com.example.socialLogin.SocialLoginPlatform;
import com.example.socialLogin.service.SocialLoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {
//    @Autowired
//    private List<SocialLoginInterface> platformList;

    @GetMapping(value = "naver")
    public String naver(@RequestParam("code") String code, @RequestParam("state") String state, 
                        @Qualifier("NaverSocialLoginService") SocialLoginInterface platform, HttpSession session) {
        return requestProfile(platform, code, state, session);
    }

    @GetMapping(value = "kakao")
    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state,
                        @Qualifier("KakaoSocialLoginService") SocialLoginInterface platform, HttpSession session) {
        return requestProfile(platform, code, state, session);
    }

    @GetMapping(value = "google")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state,
                         @Qualifier("GoogleSocialLoginService") SocialLoginInterface platform, HttpSession session) {
        return requestProfile(platform, code, state, session);
    }

    private String requestProfile(SocialLoginInterface platform, String code, String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return platform.requestProfile(code);
    }
}

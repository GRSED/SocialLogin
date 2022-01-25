package com.example.socialLogin.controller;

import com.example.socialLogin.PlatformConfig;
import com.example.socialLogin.service.SocialLoginInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {

//    @GetMapping(value = "naver")
//    public String naver(@RequestParam("code") String code, @RequestParam("state") String state,
//                        @Qualifier("NaverSocialLoginService") SocialLoginInterface platform, HttpSession session) {
//        return request(platform, code, state, session);
//    }
//
//    @GetMapping(value = "kakao")
//    public String kakao(@RequestParam("code") String code, @RequestParam("state") String state,
//                        @Qualifier("KakaoSocialLoginService") SocialLoginInterface platform, HttpSession session) {
//        return request(platform, code, state, session);
//    }

    @GetMapping(value = "{provider}")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state,
                         @PathVariable("provider") String provider, HttpSession session, SocialLoginInterface platform) {

        return request(platform, code, state, session);
    }

    private String request(SocialLoginInterface platform, String code, String state, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        return platform.requestProfile(code);
    }
}

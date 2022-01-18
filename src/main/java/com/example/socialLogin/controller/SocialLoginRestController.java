package com.example.socialLogin.controller;

import com.example.socialLogin.service.GoogleSocialLoginService;
import com.example.socialLogin.service.KakaoSocialLoginService;
import com.example.socialLogin.service.NaverSocialLoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

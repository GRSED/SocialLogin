package com.example.socialLogin.controller;

import com.example.socialLogin.SocialLoginPlatform;
import com.example.socialLogin.service.ProviderConfig;
import com.example.socialLogin.service.SocialLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {
    @Autowired
    ProviderConfig providerConfig;

    @GetMapping(value = "{provider}")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state,
                         @PathVariable("provider") String provider, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        SocialLoginService socialLoginService = providerConfig.getList().get(2);
        return socialLoginService.requestProfile(code);
    }
}

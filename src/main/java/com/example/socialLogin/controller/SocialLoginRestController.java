package com.example.socialLogin.controller;

import com.example.socialLogin.service.SocialLoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping(value = "/login/oauth2/code/")
public class SocialLoginRestController {
    @Autowired
    private Map<String, SocialLoginInterface> SocialLoginMap;

    @GetMapping(value = "{provider}")
    public String google(@RequestParam("code") String code, @RequestParam("state") String state,
                         @PathVariable("provider") String provider, HttpSession session) {
        if (!session.getAttribute("storedState").equals(state)) return "";
        SocialLoginInterface platform = SocialLoginMap.get(provider);
        return platform.requestProfile(code);
    }
}

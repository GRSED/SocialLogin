package com.example.socialLogin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocialLoginController {

    @GetMapping(value = "/naverlogin")
    public String naverlogin() {
        return "naverlogin";
    }

    @GetMapping(value = "/naver2")
    public String html() {
        return "callback";
    }
}

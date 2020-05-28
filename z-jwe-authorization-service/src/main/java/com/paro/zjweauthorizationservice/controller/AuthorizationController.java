package com.paro.zjweauthorizationservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class AuthorizationController {
    @GetMapping("/userinfo")
    public String dashboard(Model model) {
        return "userinfo";
    }
}

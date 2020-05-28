package com.paro.clientauthorizationcodegt.controller;

import com.paro.clientauthorizationcodegt.protectedResourcesModel.Patient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
@Controller
public class DashboardController {
    private final OAuth2RestTemplate restTemplate;

    public DashboardController(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String endpoint="http://localhost:8092/service";
        try {
            Patient patients=restTemplate.getForObject(endpoint, Patient.class);
            model.addAttribute("patients", patients);
        } catch (HttpClientErrorException e) {
            throw  new RuntimeException("It was not possible to retrieve user profile");
        }


        return "dashboard";
    }

    private void tryToGetUserProfile(ModelAndView mv) {
        String endpoint="http://localhost:8092/service";
        try {
            Patient patients=restTemplate.getForObject(endpoint, Patient.class);
            mv.addObject("patients", patients);
        } catch (HttpClientErrorException e) {
            throw  new RuntimeException("It was not possible to retrieve user profile");
        }
    }
}

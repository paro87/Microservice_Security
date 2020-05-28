package com.paro.clientenablesso.controller;

import com.paro.clientenablesso.clientUser.ClientUser;
import com.paro.clientenablesso.model.HealthAppSearch;
import com.paro.clientenablesso.protectedResourcesModel.Hospital;
import com.paro.clientenablesso.security.ClientUserDetails;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {
    private final OAuth2RestTemplate restTemplate;

    public ClientController(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String search(Model model) {
        ClientUserDetails clientUserDetails=(ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClientUser clientUser=clientUserDetails.getClientUser();
        model.addAttribute("user", clientUser);

        HealthAppSearch query=new HealthAppSearch();
        model.addAttribute("query", query);
        return "search";
    }
    private String hospitalClient="http://localhost:8090";
    private String departmentClient="http://localhost:8091";
    private String patientClient="http://localhost:8092";
    private static final String RESOURCE_PATH="/service/hospital/";
    private String REQUEST_URI_Department=departmentClient+RESOURCE_PATH;
    private String REQUEST_URI_Patient=patientClient+RESOURCE_PATH;
    private String REQUEST_URI_Hospital=hospitalClient+"/service/";

    @PostMapping("/search")
    public String seacrhProcess(@ModelAttribute("query") HealthAppSearch query, Model model) {
        Long hospitalId = Long.parseLong(query.getHospitalId());
        ResponseEntity<Hospital> result = restTemplate.exchange(REQUEST_URI_Hospital+hospitalId+"/with-departments-and-patients", HttpMethod.GET, null, Hospital.class);
        Hospital hospital=result.getBody();
        model.addAttribute("hospital", hospital);
        return "result";
    }

    @GetMapping("/callback")
    public String callback() {
        return "forward:/search";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/loginerror")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}

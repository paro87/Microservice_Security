package com.paro.zjweauthorizationservice.client;

import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientRegistrationService clientRegistrationService;

    public ClientController(ClientRegistrationService clientRegistrationService) {
        this.clientRegistrationService = clientRegistrationService;
    }

    @GetMapping("/register")
    public String register (Model model){
        model.addAttribute("registry", new BasicClientInfo());
        return "register";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid BasicClientInfo clientDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        Application app=new Application();
        app.setName(clientDetails.getName());
        app.addRedirectUri(clientDetails.getRedirectUri());
        app.setClientType(ClientType.valueOf(clientDetails.getClientType()));
        app.setClientId(UUID.randomUUID().toString());
        app.setClientSecret(UUID.randomUUID().toString());
        app.addScope("read_data");
        clientRegistrationService.addClientDetails(app);

        model.addAttribute("applications", clientRegistrationService.listClientDetails());
        return "dashboard";
    }

    @GetMapping("/remove")
    public String remove(Model model, @RequestParam(value="client_id", required = false) String clientId) {
        clientRegistrationService.removeClientDetails(clientId);
        model.addAttribute("applications", clientRegistrationService.listClientDetails());
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("applications", clientRegistrationService.listClientDetails());
        return "dashboard";
    }
}

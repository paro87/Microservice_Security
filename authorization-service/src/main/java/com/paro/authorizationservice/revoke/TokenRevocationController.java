package com.paro.authorizationservice.revoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TokenRevocationController {
    private final RevocationServiceFactory revocationServiceFactory;

    public TokenRevocationController(RevocationServiceFactory revocationServiceFactory) {
        this.revocationServiceFactory = revocationServiceFactory;
    }

    @PostMapping("/oauth/revoke")
    public ResponseEntity<String> revoke(@RequestParam Map<String, String> params) {
        RevocationService revocationService=revocationServiceFactory.create(params.get("token_type_hint"));
        revocationService.revoke(params.get("token"));
        return ResponseEntity.ok().build();
    }
}

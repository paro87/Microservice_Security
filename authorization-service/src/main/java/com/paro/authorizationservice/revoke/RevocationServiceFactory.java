package com.paro.authorizationservice.revoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RevocationServiceFactory {
    private final List<RevocationService> revocationServices;

    public RevocationServiceFactory(List<RevocationService> revocationServices) {
        this.revocationServices = revocationServices;
    }

    public RevocationService create(String hint) {
        return revocationServices.stream()
                .filter(service->service.supports(hint))
                .findFirst().orElse(noopRevocationService());
    }

    private RevocationService noopRevocationService() {
        return new RevocationService() {
            @Override
            public boolean supports(String tokenTypeHint) {
                return false;
            }

            @Override
            public void revoke(String token) {

            }
        };
    }
}

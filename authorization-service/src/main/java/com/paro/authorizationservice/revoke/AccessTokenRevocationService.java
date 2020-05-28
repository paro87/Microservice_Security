package com.paro.authorizationservice.revoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenRevocationService implements RevocationService {
    private final ConsumerTokenServices tokenServices;

    public AccessTokenRevocationService(@Qualifier("consumerTokenServices") ConsumerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public void revoke(String token) {
        tokenServices.revokeToken(token);
    }

    @Override
    public boolean supports(String tokenTypeHint) {
        return "access_token".equals(tokenTypeHint);
    }
}

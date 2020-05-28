package com.paro.authorizationservice.revoke;

public interface RevocationService {
    void revoke(String token);
    boolean supports(String tokenTypeHint);
}

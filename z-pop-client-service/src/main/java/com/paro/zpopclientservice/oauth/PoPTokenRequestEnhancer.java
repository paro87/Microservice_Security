package com.paro.zpopclientservice.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

//PoPTokenRequestEnhancer class - enables the client to send the public key to Authorization Server when requesting a new access token
@Component
public class PoPTokenRequestEnhancer implements RequestEnhancer {

    @Autowired
    private JwkKeyPairManager keyPairManager;

    @Override
    public void enhance(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) {
        form.add("public_key", keyPairManager.createJWK().toJSONString());
    }

}

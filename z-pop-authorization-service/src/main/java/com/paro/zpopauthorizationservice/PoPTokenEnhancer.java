package com.paro.zpopauthorizationservice;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class propagates the public_key sent by the PoP client request.
 * The PoPTokenEnhancer class is in charge to extract the public key sent by clients
 * during the token request phase. And finally, this public key is used as an
 * additional information within the JWT token
 */
class PoPTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Map<String, Object> additional = new HashMap<>();
        String publicKey = authentication.getOAuth2Request().getRequestParameters().get("public_key");
        additional.put("public_key", publicKey);

        DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;
        defaultAccessToken.setAdditionalInformation(additional);

        return accessToken;
    }
}

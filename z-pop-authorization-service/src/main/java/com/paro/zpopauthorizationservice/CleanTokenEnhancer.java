package com.paro.zpopauthorizationservice;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

//It's not a good idea to return a claim within the JWT token structure and within
//the JSON returned in response to a token request. That's just to avoid data to be
//duplicated,
public class CleanTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        accessToken.getAdditionalInformation().remove("public_key");
        return accessToken;
    }
}

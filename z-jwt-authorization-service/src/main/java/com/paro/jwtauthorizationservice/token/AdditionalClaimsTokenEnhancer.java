package com.paro.jwtauthorizationservice.token;

import com.paro.jwtauthorizationservice.authuser.ResourceOwnerUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdditionalClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Map<String, Object> additional=new HashMap<>();

        //Comment below two lines in case of in memory use try
        //ResourceOwnerUserDetails resourceOwnerUserDetails=(ResourceOwnerUserDetails) authentication.getPrincipal();
        //additional.put("email", resourceOwnerUserDetails.getEmail());

        String publicKey=authentication.getOAuth2Request().getRequestParameters().get("public_key");
        additional.put("public_key", publicKey);

        DefaultOAuth2AccessToken token =(DefaultOAuth2AccessToken) accessToken;
        System.out.println(token.getRefreshToken());;
        token.setAdditionalInformation(additional);
        return accessToken;
    }
}

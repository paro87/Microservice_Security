package com.paro.clientauthorizationcodegt.token;

import com.paro.clientauthorizationcodegt.clientUser.ClientUser;
import com.paro.clientauthorizationcodegt.clientUser.ClientUserRepository;
import com.paro.clientauthorizationcodegt.security.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Calendar;

//By implementing ClientTokenServices, the client application is able to retrieve, save, or remove an access token from its database, allowing you to choose where to save this data.
@Service
public class OAuth2ClientTokenServices implements ClientTokenServices {
    private final ClientUserRepository clientUserRepository;

    @Autowired
    public OAuth2ClientTokenServices(ClientUserRepository clientUserRepository) {
        this.clientUserRepository=clientUserRepository;
    }

    private ClientUser getClientUser(Authentication authentication) {
        ClientUserDetails loggedUser=(ClientUserDetails)authentication.getPrincipal();
        Long userId=loggedUser.getClientUser().getId();
        return clientUserRepository.findById(userId).get();
    }

    //Managing Refresh token at server side
/*    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser=getClientUser(authentication);
        String accessToken=clientUser.getAccessToken();
        Calendar expirationDate=clientUser.getAccessTokenValidity();
        if(accessToken==null)
            return null;
        DefaultOAuth2AccessToken oAuth2AccessToken=new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setExpiration(expirationDate.getTime());
        return oAuth2AccessToken;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        Calendar expirationDate=Calendar.getInstance();
        expirationDate.setTime(accessToken.getExpiration());
        ClientUser clientUser=getClientUser(authentication);
        clientUser.setAccessToken(accessToken.getValue());
        clientUser.setAccessTokenValidity(expirationDate);
        clientUserRepository.save(clientUser);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser=getClientUser(authentication);
        clientUser.setAccessToken(null);
        clientUser.setRefreshToken(null);
        clientUser.setAccessTokenValidity(null);
        clientUserRepository.save(clientUser);
    }

    */

    //Managing Refresh token at client side
    //STARTS
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser=getClientUser(authentication);
        String accessToken=clientUser.getAccessToken();
        Calendar expirationDate=clientUser.getAccessTokenValidity();
        if(accessToken==null)
            return null;
        DefaultOAuth2AccessToken oAuth2AccessToken=new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setExpiration(expirationDate.getTime());
        oAuth2AccessToken.setRefreshToken(new DefaultOAuth2RefreshToken(clientUser.getRefreshToken()));
        return oAuth2AccessToken;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        Calendar expirationDate=Calendar.getInstance();
        expirationDate.setTime(accessToken.getExpiration());
        ClientUser clientUser=getClientUser(authentication);
        clientUser.setAccessToken(accessToken.getValue());
        clientUser.setAccessTokenValidity(expirationDate);
        clientUser.setRefreshToken(accessToken.getRefreshToken().getValue());
        clientUserRepository.save(clientUser);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser=getClientUser(authentication);
        clientUser.setAccessToken(null);
        clientUser.setRefreshToken(null);
        clientUser.setAccessTokenValidity(null);
        clientUserRepository.save(clientUser);
    }
    //ENDS
}

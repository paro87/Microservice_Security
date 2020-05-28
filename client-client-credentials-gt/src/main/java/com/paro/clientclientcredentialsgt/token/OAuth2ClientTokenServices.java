package com.paro.clientclientcredentialsgt.token;

import com.paro.clientclientcredentialsgt.clientUser.ClientUser;
import com.paro.clientclientcredentialsgt.clientUser.ClientUserRepository;
import com.paro.clientclientcredentialsgt.security.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Calendar;

//By implementing ClientTokenServices, the client application is able to retrieve, save, or remove an access token from its database, allowing you to choose where to save this data.
@Service
public class OAuth2ClientTokenServices implements ClientTokenServices {
    private final SettingsRepository settings;

    public OAuth2ClientTokenServices(SettingsRepository settings) {
        this.settings = settings;
    }

    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        String accessToken = settings.getAccessToken();
        Calendar expirationDate = settings.getExpiresIn();
        if (accessToken == null) return null;
        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setExpiration(expirationDate.getTime());
        return oAuth2AccessToken;
    }
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(accessToken.getExpiration());
        settings.setAccessToken(accessToken.getValue());
        settings.setExpiresIn(expirationDate);
    }
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        settings.setAccessToken(null);
        settings.setExpiresIn(null);
    }
}

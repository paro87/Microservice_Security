package com.paro.clientpasswordgt.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
//@EnableOAuth2Sso
@EnableOAuth2Client
public class ClientConfiguration {
    private final ClientTokenServices clientTokenServices;
    private final OAuth2ClientContext oAuth2ClientContext;

    public ClientConfiguration(ClientTokenServices clientTokenServices, @Qualifier("oauth2ClientContext") OAuth2ClientContext oAuth2ClientContext) {
        this.clientTokenServices = clientTokenServices;
        this.oAuth2ClientContext = oAuth2ClientContext;
    }

    //This bean allows us to set up all the required data that is used for authorization and token request phases.
    @Bean
    public OAuth2ProtectedResourceDetails passwordResourceDetails() {
        //Spring Security OAuth2 provides the resource details of concrete classes for each grant type described by the OAuth 2.0 protocol
        ResourceOwnerPasswordResourceDetails resourceDetails=new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setId("oauth2server");
        resourceDetails.setTokenName("oauth_token");
        resourceDetails.setClientId("clientPasswordGT");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setAccessTokenUri("http://localhost:8100/oauth/token");
        resourceDetails.setScope(Arrays.asList("read_data"));
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);

        return resourceDetails;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate () {

        OAuth2ProtectedResourceDetails resourceDetails= passwordResourceDetails();
        OAuth2RestTemplate restTemplate=new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);
        AccessTokenProviderChain providerChain=new AccessTokenProviderChain(Arrays.asList(new ResourceOwnerPasswordAccessTokenProvider()));
        providerChain.setClientTokenServices(clientTokenServices);
        restTemplate.setAccessTokenProvider(providerChain);
        return restTemplate;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }



}

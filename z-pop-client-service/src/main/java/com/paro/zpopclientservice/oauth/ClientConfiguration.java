package com.paro.zpopclientservice.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class ClientConfiguration {

    private final ClientTokenServices clientTokenServices;
    private final OAuth2ClientContext oauth2ClientContext;
    private final HttpRequestWithPoPSignatureInterceptor interceptor;
    private final PoPTokenRequestEnhancer requestEnhancer;

    public ClientConfiguration(ClientTokenServices clientTokenServices, @Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext, HttpRequestWithPoPSignatureInterceptor interceptor, PoPTokenRequestEnhancer requestEnhancer) {
        this.clientTokenServices = clientTokenServices;
        this.oauth2ClientContext = oauth2ClientContext;
        this.interceptor = interceptor;
        this.requestEnhancer = requestEnhancer;
    }

    @Bean
    public AuthorizationCodeResourceDetails authorizationCode() {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();

        resourceDetails.setId("oauth2server");
        resourceDetails.setTokenName("oauth_token");
        resourceDetails.setClientId("clientapp");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
        resourceDetails.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
        resourceDetails.setScope(Arrays.asList("read_profile"));
        resourceDetails.setPreEstablishedRedirectUri(("http://localhost:9000/callback"));
        resourceDetails.setUseCurrentUri(false);
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);

        return resourceDetails;
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate() {
        OAuth2ProtectedResourceDetails resourceDetails = authorizationCode();
        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);

        AuthorizationCodeAccessTokenProvider authorizationCode = new AuthorizationCodeAccessTokenProvider();
        authorizationCode.setTokenRequestEnhancer(requestEnhancer);

        AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(authorizationCode));

        provider.setClientTokenServices(clientTokenServices);
        template.setAccessTokenProvider(provider);
        template.setInterceptors(Arrays.asList(interceptor));

        return template;
    }

}

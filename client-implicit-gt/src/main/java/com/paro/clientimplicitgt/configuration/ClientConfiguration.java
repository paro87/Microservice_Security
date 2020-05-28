package com.paro.clientimplicitgt.configuration;

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
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
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
    public OAuth2ProtectedResourceDetails implicitResourceDetails() {
        //Spring Security OAuth2 provides the resource details of concrete classes for each grant type described by the OAuth 2.0 protocol
        ImplicitResourceDetails resourceDetails=new ImplicitResourceDetails();
        //resourceDetails.setId("oauth2server");
        resourceDetails.setTokenName("acsess_token");
        resourceDetails.setClientId("clientImplicitGT");
        resourceDetails.setClientSecret("123456");
        //resourceDetails.setClientSecret("$2a$04$SiCp/ZGQgC.f8dXCntym7uF.1JAHlfuEvd4NSbNDB4r0kvvmN1dVG");
        //resourceDetails.setAccessTokenUri("http://localhost:8100/oauth/token");
        resourceDetails.setUserAuthorizationUri("http://localhost:8100/oauth/authorize");
        resourceDetails.setScope(Arrays.asList("read_data"));
        resourceDetails.setPreEstablishedRedirectUri(("http://localhost:9002/callback"));
        resourceDetails.setUseCurrentUri(false);
        //System.out.println("Pre Established Redirect Uri "+resourceDetails.getPreEstablishedRedirectUri());
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.query);
        return resourceDetails;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate () {

        OAuth2ProtectedResourceDetails resourceDetails= implicitResourceDetails();
        OAuth2RestTemplate restTemplate=new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);
        System.out.println(oAuth2ClientContext.getAccessToken());
        AccessTokenProviderChain providerChain=new AccessTokenProviderChain(Arrays.asList(new CustomImplicitAccessTokenProvider()));
        providerChain.setClientTokenServices(clientTokenServices);
        restTemplate.setAccessTokenProvider(providerChain);

        return restTemplate;
    }

//    @Bean
//    public OAuth2RestTemplate oAuth2RestTemplate () {
//        ResourceOwnerPasswordResourceDetails resource=new ResourceOwnerPasswordResourceDetails();
//        resource.setAccessTokenUri("http://localhost:8100/oauth/token");
//        resource.setClientId("clientAuthorizationCodeGT");
//        resource.setClientSecret("123456");
//        resource.setGrantType("authorization_code");
//        resource.setClientAuthenticationScheme(AuthenticationScheme.header);
//
//
//
//        OAuth2RestTemplate restTemplate=new OAuth2RestTemplate(resource, oAuth2ClientContext);
//        AccessTokenProviderChain providerChain=new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
//        providerChain.setClientTokenServices(clientTokenServices);
//        restTemplate.setAccessTokenProvider(providerChain);
//        return restTemplate;
//    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }



}

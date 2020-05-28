/*
package com.paro.authorizationservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServiceConfig extends AuthorizationServerConfigurerAdapter {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServiceConfig(AuthenticationManager authenticationManager, @Qualifier("userDetailsServiceBean") UserDetailsService userDetailsService, DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.authenticationManager=authenticationManager;
        this.userDetailsService=userDetailsService;
        this.dataSource = dataSource;                               //For client details saved in database
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())                           //For client details saved in database
                .approvalStore(approvalStore());                    //For client details saved in database
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);                //For client details saved in database
    }

    //Client details saved in-memory
*/
/*    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("clientAuthorizationCodeGT").secret("{noop}123456")
                .redirectUris("http://localhost:9001/callback")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .accessTokenValiditySeconds(120)    //Added for refresh token support
                .scopes("read_data")

                .and()

                .withClient("clientImplicitGT").secret("{noop}123456")
                .redirectUris("http://localhost:9002/callback")
                .authorizedGrantTypes("implicit")
                .accessTokenValiditySeconds(120)
                .scopes("read_data")

                .and()

                .withClient("clientPasswordGT").secret("{noop}123456")
                .redirectUris("http://localhost:9003/callback")
                .authorizedGrantTypes("password" ,"refresh_token")
                .accessTokenValiditySeconds(120)    //Added for refresh token support
                .scopes("read_data")

                .and()
                //callback Uri ("http://localhost:9004/callback")
                .withClient("clientClientCredentialsGT")
                .secret("{noop}123456")
                .authorizedGrantTypes("client_credentials")
                .scopes("read_data");
    }*//*




    //Client details saved in database
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    //For client details saved in database
    //START
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }
    //END

    //Client registration
    @Bean
    public ClientRegistrationService clientRegistrationService() {
        return new JdbcClientDetailsService(dataSource);
    }

//*Refresh Tokens
//     * 1) Authorization GT -
//     *
//     * 2) Implicit GT - when using the Implicit grant type, it's important to bear in mind that this grant type is not
//     * allowed to issue a refresh token as per the OAuth 2.0 specification. This behavior makes
//     * sense because as the user must be present when using an application that runs within the
//     * browser, then the user can always authorize the third-party application again if needed.
//     * Moreover, the Authorization Server has plenty of conditions to recognize the user's session
//     * and avoid asking the Resource Owner to authenticate and authorize the client again.
//     * Another reason for not issuing a refresh token for the Implicit grant type, is because this
//     * grant type is aimed at public applications that are not able to protect confidential data, as is
//     * the case with refresh tokens.
//     *
//     * 3) Password GT -
//     *
//     * 4) Client Credentials GT - It's important to note that when using the Client Credentials grant type, as per the OAuth
//     * 2.0 specification, the Authorization Server should not issue a refreshed token because there
//     * is no reason to worry about the user experience in this case. When using Client Credentials,
//     * the client by itself is able to request a new access token in case of an expiration.


}
*/

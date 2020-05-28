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
public class AuthorizationServiceConfig2 extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public AuthorizationServiceConfig2(DataSource dataSource, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;

        this.authenticationManager = authenticationManager;
    }

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())                           //For client details saved in database
                .approvalStore(approvalStore());                    //For client details saved in database
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStores(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("hasAuthority('introspection')");
        security.passwordEncoder(passwordEncoder);
    }

    //Client details saved in-memory
/*    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("clientAuthorizationCodeGT").secret("{noop}123456")
                .redirectUris("http://localhost:9001/callback")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .accessTokenValiditySeconds(120)    //Added for refresh token support
                .refreshTokenValiditySeconds(240000)
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
    }*/

    //Client details saved in database
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    //Client registration in database
    @Bean
    public ClientRegistrationService clientRegistrationService() {
        return new JdbcClientDetailsService(dataSource);
    }



}

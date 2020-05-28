/*
package com.paro.jwtauthorizationservice.configuration;

import com.paro.jwtauthorizationservice.token.AdditionalClaimsTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServiceConfigInMemory extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdditionalClaimsTokenEnhancer tokenEnhancer;

    public AuthorizationServiceConfigInMemory(DataSource dataSource, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AdditionalClaimsTokenEnhancer tokenEnhancer) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenEnhancer = tokenEnhancer;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain chain=new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter()));
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(chain)
                .accessTokenConverter(accessTokenConverter())
                .reuseRefreshTokens(false);
    }

    //Client details saved in memory
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String password=passwordEncoder.encode("123456");
        clients.inMemory()
                .withClient("clientAuthorizationCodeGT").secret(password)
                .redirectUris("http://localhost:9001/callback")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .accessTokenValiditySeconds(120)    //Added for refresh token support
                .scopes("read_data")

                .and()

                .withClient("clientImplicitGT").secret(password)
                .redirectUris("http://localhost:9002/callback")
                .authorizedGrantTypes("implicit")
                .accessTokenValiditySeconds(120)
                .scopes("read_data")

                .and()

                .withClient("clientPasswordGT").secret(password)
                .redirectUris("http://localhost:9003/callback")
                .authorizedGrantTypes("password" ,"refresh_token")
                .accessTokenValiditySeconds(120)    //Added for refresh token support
                .scopes("read_data")

                .and()
                //callback Uri ("http://localhost:9004/callback")
                .withClient("clientClientCredentialsGT")
                .secret(password)
                .authorizedGrantTypes("client_credentials")
                .scopes("read_data");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
        security.tokenKeyAccess("permitAll()");                     //For Assymetric Key signing
        //Default Endpoint - /oauth/token_key - to retrieve the public key, which is required by the Resource Server to validate oncoming JWT access tokens.
        //Ex/ http://localhost:8100/oauth/token_key
        //Spring Security OAuth exposes two endpoints for checking tokens (/oauth/check_token and /oauth/token_key). Those endpoints are not exposed by default (have access "denyAll()").
    }

    //Symmetric Key Signing
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter=new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("non-prod-signature");
        return jwtAccessTokenConverter;
    }

*/
/*    //Assymetric Key Signing
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter=new JwtAccessTokenConverter();
        try{
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            keyPairGenerator.initialize(1024, random);
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            //KeyPair - holds the private and public key that have to be used to sign and to validate an access token, respectively
            jwtAccessTokenConverter.setKeyPair(keyPair);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwtAccessTokenConverter;
    }*//*


    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    //Client registration in database
    @Bean
    public ClientRegistrationService clientRegistrationService() {
        return new JdbcClientDetailsService(dataSource);
    }
}
*/

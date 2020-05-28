package com.paro.zjweauthorizationservice.configuration;

import com.paro.zjweauthorizationservice.jwt.JweTokenEnhancer;
import com.paro.zjweauthorizationservice.jwt.JweTokenSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServiceConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthorizationServiceConfig(DataSource dataSource, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
         endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(jwtTokenStore())
                .tokenEnhancer(tokenEnhancer())
                .accessTokenConverter(accessTokenConverter());
    }

    //Client details saved in memory
    /*@Override
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
    }*/

    //Client details saved in database
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
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
    /*
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter=new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("non-prod-signature");
        return jwtAccessTokenConverter;
    }*/

    //Assymetric Key Signing
    /*@Bean
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
    }*/

    //JWE
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(symmetricKey());
        return tokenConverter;
    }
    //JWE
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JweTokenEnhancer(accessTokenConverter(), new JweTokenSerializer(symmetricKey()));
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    //Client registration in database
    @Bean
    public ClientRegistrationService clientRegistrationService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public String symmetricKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

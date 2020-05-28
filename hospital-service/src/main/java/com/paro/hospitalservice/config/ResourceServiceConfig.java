package com.paro.hospitalservice.config;

import com.paro.hospitalservice.security.ResourceOwnerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableResourceServer
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {
    private ResourceOwnerUserDetails resourceOwnerUserDetails;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ResourceServiceConfig(ResourceOwnerUserDetails resourceOwnerUserDetails, DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.resourceOwnerUserDetails = resourceOwnerUserDetails;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated().and()
                .requestMatchers().antMatchers("/service/**");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }



    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices=new RemoteTokenServices();
        tokenServices.setClientId("resource_server");
        tokenServices.setClientSecret("123456");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8100/oauth/check_token");
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter converter=new DefaultAccessTokenConverter();
        converter.setUserTokenConverter(userTokenConverter());
        return converter;
    }

    @Bean
    public UserAuthenticationConverter userTokenConverter() {
        DefaultUserAuthenticationConverter converter=new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(resourceOwnerUserDetails);
        return converter;
    }

}

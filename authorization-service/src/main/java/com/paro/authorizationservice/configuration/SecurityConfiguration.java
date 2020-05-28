package com.paro.authorizationservice.configuration;

import com.paro.authorizationservice.authuser.ResourceOwnerUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ResourceOwnerUserDetails resourceOwnerUserDetails;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(ResourceOwnerUserDetails resourceOwnerUserDetails, PasswordEncoder passwordEncoder) {
        this.resourceOwnerUserDetails = resourceOwnerUserDetails;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(resourceOwnerUserDetails).passwordEncoder(passwordEncoder);
    }

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests().antMatchers("/login", "/oauth/**", "/oauth/check_token", "/oauth/authorize", "/")
//                .permitAll().anyRequest().authenticated().and()
//                .formLogin().and()
//                .logout().permitAll()
//                .and()
//                .csrf().disable();
    }


}

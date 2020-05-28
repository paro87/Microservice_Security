package com.paro.jwtauthorizationservice.configuration;

import com.paro.jwtauthorizationservice.authuser.ResourceOwnerUserDetailsService;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ResourceOwnerUserDetailsService resourceOwnerUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(ResourceOwnerUserDetailsService resourceOwnerUserDetailsService, PasswordEncoder passwordEncoder) {
        this.resourceOwnerUserDetailsService = resourceOwnerUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/oauth**")
                .permitAll().anyRequest().authenticated().and()
                .formLogin().and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(resourceOwnerUserDetailsService).passwordEncoder(passwordEncoder);
    }


}

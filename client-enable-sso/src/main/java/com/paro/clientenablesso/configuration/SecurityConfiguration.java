package com.paro.clientenablesso.configuration;

import com.paro.clientenablesso.security.ClientUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1000)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final ClientUserDetailsService clientUserDetailsService;
    private final BCryptPasswordEncoder encoder;



    @Autowired
    public SecurityConfiguration(ClientUserDetailsService clientUserDetailsService, BCryptPasswordEncoder encoder) {
        this.clientUserDetailsService = clientUserDetailsService;
        this.encoder = encoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clientUserDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/", "/index.html", "/search.html", "/result.html")
//                .permitAll();

//        http.authorizeRequests().antMatchers("/")
//                .permitAll().anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login.html")
//                .defaultSuccessUrl("/index.html", true)
//                .failureUrl("/loginerror.html")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login.html")
//                .permitAll();

        http.authorizeRequests().antMatchers("/", "/index.html")
                .permitAll().anyRequest().authenticated().and()
                .formLogin().and()
                .logout().permitAll()
                .and()
                .csrf().disable();

//        http.authorizeRequests().antMatchers("/", "/login.html")
//                .permitAll().anyRequest().authenticated().and()
//                .formLogin().and()
//                .logout().permitAll();

    }
}

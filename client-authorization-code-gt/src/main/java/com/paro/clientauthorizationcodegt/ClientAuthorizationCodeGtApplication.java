package com.paro.clientauthorizationcodegt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class ClientAuthorizationCodeGtApplication implements ServletContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ClientAuthorizationCodeGtApplication.class, args);
        //encrypt();
    }

    //One important thing that we have done is to define another name for JSESSIONID cookie.
    //We did this because as the OAuth Provider and the client application are running at the
    //same location (which is localhost), one application will override the cookie created by the
    //other application, leading us to the impossibility of saving states between redirections
    //performed by the OAuth 2.0 protocol.
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("client-session");
    }

/*    public static void encrypt() {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(4);
        String pass=bCryptPasswordEncoder.encode("client");
        System.out.println(pass);
        System.out.println(bCryptPasswordEncoder.matches("client", pass));
    }*/
}

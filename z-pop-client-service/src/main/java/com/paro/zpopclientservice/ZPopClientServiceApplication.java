package com.paro.zpopclientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class ZPopClientServiceApplication implements ServletContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ZPopClientServiceApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("clientsession");
    }

}

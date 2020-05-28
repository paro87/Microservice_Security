package com.paro.authorizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthorizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
        //encrypt();
    }
//    public static void encrypt() {
//        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(4);
//        String pass=bCryptPasswordEncoder.encode("auth");
//        System.out.println(pass);
//        System.out.println(bCryptPasswordEncoder.matches("auth", pass));
//    }

}

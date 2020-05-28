package com.paro.jwtauthorizationservice.authuser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ResourceOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;

    public ResourceOwner() {
    }

    public ResourceOwner(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

package com.paro.jwtauthorizationservice.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicClientInfo {
    private String name;
    private String redirectUri;
    private String clientType;

}

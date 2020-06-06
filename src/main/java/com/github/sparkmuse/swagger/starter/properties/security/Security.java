package com.github.sparkmuse.swagger.starter.properties.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Security {
    private Boolean basic;
    private Api api;
    private Oauth oauth;
}

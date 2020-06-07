package com.github.sparkmuse.swagger.starter.security.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@NoArgsConstructor
public class Security {
    private Boolean basic;

    @NestedConfigurationProperty
    private Api api;

    @NestedConfigurationProperty
    private Oauth oauth;
}

package com.github.sparkmuse.swagger.starter.security.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Oauth {

    @NestedConfigurationProperty
    private TokenRequestEndPoint tokenRequest;

    @NestedConfigurationProperty
    private TokenEndPoint token;

    private List<String> scopes = new ArrayList<>();
}

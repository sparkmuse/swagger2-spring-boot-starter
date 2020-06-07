package com.github.sparkmuse.swagger.starter;

import com.github.sparkmuse.swagger.starter.apiinfo.ApiInfoProperties;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String group;
    private String pathAntExpressions;

    @NestedConfigurationProperty
    private ApiInfoProperties apiInfo;

    @NestedConfigurationProperty
    private Security security;
}



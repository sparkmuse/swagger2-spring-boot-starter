package com.github.sparkmuse.swagger.starter.properties;

import com.github.sparkmuse.swagger.starter.properties.ApiInfo;
import com.github.sparkmuse.swagger.starter.properties.security.Security;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String group;
    private String pathAntExpressions;
    private ApiInfo apiInfo;
    private Security security;
}



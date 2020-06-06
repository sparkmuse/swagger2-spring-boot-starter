package com.github.sparkmuse.swagger.starter;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String group;
    private String pathAntExpressions;
    private ApiInfo apiInfo;

    @Data
    @NoArgsConstructor
    public static class ApiInfo {
        private String version;
        private String title;
        private String description;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
        private Contact contact;
    }

    @Data
    @NoArgsConstructor
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }
}



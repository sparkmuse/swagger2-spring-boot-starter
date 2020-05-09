package com.github.sparkmuse.swagger.starter;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Configuration
@EnableSwagger2
@ConditionalOnClass(Swagger2DocumentationConfiguration.class)
@EnableConfigurationProperties({SwaggerProperties.class})
public class Swagger2AutoConfiguration {

    private static String DEFAULT_GROUP = "api";

    @Bean
    @ConditionalOnMissingBean
    public Docket docket(SwaggerProperties swaggerProperties) {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getGroup() == null ? DEFAULT_GROUP : swaggerProperties.getGroup())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo(swaggerProperties));
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {

        SwaggerProperties.ApiInfo apiInfo = swaggerProperties.getApiInfo();

        if (apiInfo == null) {
            return ApiInfo.DEFAULT;
        }

        // Final object to be returned
        MutableApiInfo result = getMutableApiInfo(apiInfo);

        // Check the contact
        SwaggerProperties.Contact contact = apiInfo.getContact();
        if (contact != null) {
            result.setContact(new MutableContact());
            result.getContact().setName(defaultIfNull(contact.getName(), ApiInfo.DEFAULT_CONTACT.getName()));
            result.getContact().setEmail(defaultIfNull(contact.getEmail(), ApiInfo.DEFAULT_CONTACT.getEmail()));
            result.getContact().setUrl(defaultIfNull(contact.getUrl(), "ApiInfo.DEFAULT_CONTACT.getUrl()"));
        }

        return  MutableApiInfo.toApiInfo(result);
    }

    private MutableApiInfo getMutableApiInfo(SwaggerProperties.ApiInfo apiInfo) {
        MutableApiInfo result = new MutableApiInfo();

        result.setTitle(defaultIfNull(apiInfo.getTitle(), ApiInfo.DEFAULT.getTitle()));
        result.setDescription(defaultIfNull(apiInfo.getDescription(), ApiInfo.DEFAULT.getDescription()));
        result.setLicense(defaultIfNull(apiInfo.getLicense(), ApiInfo.DEFAULT.getLicense()));
        result.setLicenseUrl(defaultIfNull(apiInfo.getLicenseUrl(), ApiInfo.DEFAULT.getLicenseUrl()));
        result.setTermsOfServiceUrl(defaultIfNull(apiInfo.getTermsOfServiceUrl(), ApiInfo.DEFAULT.getTermsOfServiceUrl()));
        result.setVersion(defaultIfNull(apiInfo.getVersion(), ApiInfo.DEFAULT.getVersion()));
        result.setVendorExtensions(ApiInfo.DEFAULT.getVendorExtensions());
        return result;
    }
}
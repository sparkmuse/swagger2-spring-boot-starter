package com.github.sparkmuse.swagger.starter;

import com.github.sparkmuse.swagger.starter.apiinfo.ApiInfoProvider;
import com.github.sparkmuse.swagger.starter.security.ApiSecurityProvider;
import com.github.sparkmuse.swagger.starter.security.BasicSecurityProvider;
import com.github.sparkmuse.swagger.starter.security.SecurityProvider;
import com.github.sparkmuse.swagger.starter.security.properties.Api;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Swagger2AutoConfigurationTest {

    private static final ApiInfo API_INFO = new ApiInfo(
            "Api Documentation",
            "Api Documentation",
            "1.0",
            "urn:tos",
            new Contact("Contact name", "contact.url", "contact@email.com"),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>());
    @Mock
    private ApiInfoProvider apiInfoProvider;

    @Mock
    private BasicSecurityProvider basicSecurityProvider;

    @Mock
    private ApiSecurityProvider apiSecurityProvider;

    private Swagger2AutoConfiguration configuration;

    @BeforeEach
    void setUp() {
        List<SecurityProvider> securityProviders = List.of(basicSecurityProvider, apiSecurityProvider);
        configuration = new Swagger2AutoConfiguration(securityProviders, apiInfoProvider);
    }

    @Test
    @DisplayName("configures docket")
    void defaults() {

        Docket expected = new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .select()
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.emptyList())
                .apiInfo(API_INFO);

        when(apiInfoProvider.get(any())).thenReturn(API_INFO);

        Docket actual = configuration.docket(new SwaggerProperties());

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("apiSelector")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("configures docket with ant paths")
    void defaultsWithPath() {

        Docket expected = new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .securitySchemes(Collections.emptyList())
                .apiInfo(API_INFO);

        when(apiInfoProvider.get(any())).thenReturn(API_INFO);

        SwaggerProperties swaggerProperties = mock(SwaggerProperties.class);
        when(swaggerProperties.getPathAntExpressions()).thenReturn("/api/**");
        Docket actual = configuration.docket(swaggerProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("apiSelector")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("configures docket with security")
    void security() {

        BasicAuth basicScheme = new BasicAuth("Basic");
        SecurityScheme apiScheme = new ApiKey("Api", "key-name", ApiKeyVehicle.HEADER.getValue());

        Docket expected = new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .select()
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(basicScheme, apiScheme))
                .apiInfo(API_INFO);

        when(apiInfoProvider.get(any())).thenReturn(API_INFO);
        when(basicSecurityProvider.getScheme(any())).thenReturn(Optional.of(basicScheme));
        when(apiSecurityProvider.getScheme(any())).thenReturn((Optional.of(apiScheme)));

        SwaggerProperties swaggerProperties = mock(SwaggerProperties.class);
        when(swaggerProperties.getSecurity()).thenReturn(getBasicAndApiSecurity());

        Docket actual = configuration.docket(swaggerProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("apiSelector")
                .isEqualTo(expected);
    }

    private Security getBasicAndApiSecurity() {
        Security security = new Security();
        security.setBasic(true);

        Api api = new Api();
        api.setKeyName("key-name");
        api.setVehicle(ApiKeyVehicle.HEADER);
        security.setApi(api);
        return security;
    }
}
package com.github.sparkmuse.swagger.starter;

import com.github.sparkmuse.swagger.starter.apiinfo.ApiInfoProvider;
import com.github.sparkmuse.swagger.starter.security.SecurityProvider;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableSwagger2
@ConditionalOnClass(Swagger2DocumentationConfiguration.class)
@EnableConfigurationProperties({SwaggerProperties.class})
@ComponentScan(basePackageClasses = Swagger2AutoConfiguration.class)
@RequiredArgsConstructor
public class Swagger2AutoConfiguration {

    private static final String DEFAULT_GROUP = "api";
    private static final String ANT_EXPRESSION_SEPARATOR = ",";

    private final List<SecurityProvider> securityProviders;
    private final ApiInfoProvider apiInfoProvider;

    @Bean
    @ConditionalOnMissingBean
    public Docket docket(SwaggerProperties swaggerProperties) {

        ApiInfo apiInfo = apiInfoProvider.get(swaggerProperties.getApiInfo());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getGroup() == null ? DEFAULT_GROUP : swaggerProperties.getGroup())
                .select()
                .paths(getPaths(swaggerProperties))
                .build()
                .securitySchemes(securitySchemes(swaggerProperties))
                .apiInfo(apiInfo);
    }

    private List<SecurityScheme> securitySchemes(SwaggerProperties swaggerProperties) {

        if (swaggerProperties.getSecurity() == null) {
            return Collections.emptyList();
        }

        Security security = swaggerProperties.getSecurity();

        return securityProviders.stream()
                .map(p -> p.getScheme(security))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Predicate<String> getPaths(SwaggerProperties swaggerProperties) {

        if (swaggerProperties.getPathAntExpressions() == null) {
            return PathSelectors.any();
        }
        return Stream.of(swaggerProperties.getPathAntExpressions().split(ANT_EXPRESSION_SEPARATOR))
                .map(PathSelectors::ant)
                .reduce(e -> false, Predicates::or);
    }
}
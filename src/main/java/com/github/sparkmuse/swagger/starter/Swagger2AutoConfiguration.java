package com.github.sparkmuse.swagger.starter;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableSwagger2
@ConditionalOnClass(Swagger2DocumentationConfiguration.class)
@EnableConfigurationProperties({SwaggerProperties.class})
public class Swagger2AutoConfiguration {

    private static final String DEFAULT_GROUP = "api";

    public static final Contact DEFAULT_CONTACT =
            new Contact("Contact Name", "contact.url", "contact@email.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Api Documentation",
            "Api Documentation",
            "1.0",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>());


    @Bean
    @ConditionalOnMissingBean
    public Docket docket(SwaggerProperties swaggerProperties) {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProperties.getGroup() == null ? DEFAULT_GROUP : swaggerProperties.getGroup())
                .select()
                .paths(getPaths(swaggerProperties))
                .build()
                .apiInfo(apiInfo(swaggerProperties));
    }

    private Predicate<String> getPaths(SwaggerProperties swaggerProperties) {

        if (swaggerProperties.getPathAntExpressions() == null) {
            return PathSelectors.any();
        }
        return Arrays.stream(swaggerProperties.getPathAntExpressions().split(","))
                    .map(PathSelectors::ant)
                    .reduce(e -> false, Predicates::or);
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {

        SwaggerProperties.ApiInfo apiInfo = swaggerProperties.getApiInfo();

        if (apiInfo == null) {
            return DEFAULT_API_INFO;
        }

        // Final object to be returned
        MutableApiInfo result = getMutableApiInfo(apiInfo);

        // Check the contact
        SwaggerProperties.Contact contact = apiInfo.getContact();
        if (contact != null) {
            MutableContact resultContact = new MutableContact();
            resultContact.name(contact.getName() == null ? DEFAULT_CONTACT.getName() : contact.getName());
            resultContact.name(contact.getEmail() == null ? DEFAULT_CONTACT.getEmail() : contact.getEmail());
            resultContact.name(contact.getUrl() == null ? DEFAULT_CONTACT.getUrl() : contact.getUrl());
            result.contact(resultContact);
        }

        return MutableApiInfo.toApiInfo(result);
    }

    private MutableApiInfo getMutableApiInfo(SwaggerProperties.ApiInfo apiInfo) {

        MutableApiInfo result = new MutableApiInfo();

        result.title(apiInfo.getTitle() == null ? DEFAULT_API_INFO.getTitle() : apiInfo.getTitle());
        result.description(apiInfo.getDescription() == null ? DEFAULT_API_INFO.getDescription() : apiInfo.getDescription());
        result.license(apiInfo.getLicense() == null ? DEFAULT_API_INFO.getLicense() : apiInfo.getLicense());
        result.licenseUrl(apiInfo.getLicenseUrl() == null ? DEFAULT_API_INFO.getLicenseUrl() : apiInfo.getLicenseUrl());
        result.termsOfServiceUrl(apiInfo.getTermsOfServiceUrl() == null ? DEFAULT_API_INFO.getTermsOfServiceUrl() : apiInfo.getTermsOfServiceUrl());
        result.version(apiInfo.getVersion() == null ? DEFAULT_API_INFO.getVersion() : apiInfo.getVersion());
        result.vendorExtensions(DEFAULT_API_INFO.getVendorExtensions());

        return result;
    }
}
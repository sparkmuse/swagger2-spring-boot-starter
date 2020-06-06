package com.github.sparkmuse.swagger.starter;

import com.github.sparkmuse.swagger.starter.properties.SwaggerProperties;
import com.github.sparkmuse.swagger.starter.properties.security.Oauth;
import com.github.sparkmuse.swagger.starter.properties.security.TokenEndPoint;
import com.github.sparkmuse.swagger.starter.properties.security.TokenRequestEndPoint;
import com.github.sparkmuse.swagger.starter.properties.mutable.MutableApiInfo;
import com.github.sparkmuse.swagger.starter.properties.mutable.MutableContact;
import com.github.sparkmuse.swagger.starter.properties.security.Security;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.TokenEndpointBuilder;
import springfox.documentation.builders.TokenRequestEndpointBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .securitySchemes(securitySchemes(swaggerProperties))
//                .securityContexts(Arrays.asList(securityContext(swaggerProperties)))
                .apiInfo(apiInfo(swaggerProperties));
    }


    private List<SecurityScheme> securitySchemes(SwaggerProperties swaggerProperties) {

        if (swaggerProperties == null || swaggerProperties.getSecurity() == null) {
            return Collections.emptyList();
        }

        Security security = swaggerProperties.getSecurity();

        if (security.getBasic() != null && security.getBasic()) {
            return List.of(new BasicAuth("basic"));
        }

        if (security.getApi() != null) {
            Assert.notNull(security.getApi().getKeyName(), "Api key name cannot be null");
            Assert.notNull(security.getApi().getVehicle(), "Api key vehicle cannot be null");
            return List.of(new ApiKey("api",
                    security.getApi().getKeyName(),
                    security.getApi().getVehicle().getValue()));
        }

        Oauth oauth = security.getOauth();
        if (oauth != null) {
            List.of(new OAuth("oauth", scopes(oauth.getScopes()), grantTypes(oauth)));
        }

        return Collections.emptyList();

    }

    private List<GrantType> grantTypes(Oauth oauth) {

        TokenRequestEndPoint tokenRequest = oauth.getTokenRequest();
        Assert.notNull(tokenRequest, "Token request endpoint cannot be null");

        TokenRequestEndpoint tokenRequestEndPoint = new TokenRequestEndpointBuilder()
                .url(tokenRequest.getUrl())
                .clientIdName(tokenRequest.getClientIdName())
                .clientSecretName(tokenRequest.getClientSecretName())
                .build();

        TokenEndPoint token = oauth.getToken();
        Assert.notNull(token, "Token endpoint cannot be null");

        TokenEndpoint tokenEndPoint = new TokenEndpointBuilder()
                .url(token.getUrl())
                .tokenName(token.getTokenName())
                .build();

        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenRequestEndpoint(tokenRequestEndPoint)
                .tokenEndpoint(tokenEndPoint)
                .build();
        return List.of(grantType);
    }

    private static List<AuthorizationScope> scopes(List<String> scopes) {
        return scopes.stream()
                .map(scope -> new AuthorizationScope(scope, scope + " description"))
                .collect(Collectors.toList());
    }

    private Predicate<String> getPaths(SwaggerProperties swaggerProperties) {

        if (swaggerProperties.getPathAntExpressions() == null) {
            return PathSelectors.any();
        }
        return Stream.of(swaggerProperties.getPathAntExpressions().split(","))
                .map(PathSelectors::ant)
                .reduce(e -> false, Predicates::or);
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {

        com.github.sparkmuse.swagger.starter.properties.ApiInfo apiInfo = swaggerProperties.getApiInfo();

        if (apiInfo == null) {
            return DEFAULT_API_INFO;
        }

        // Final object to be returned
        MutableApiInfo result = getMutableApiInfo(apiInfo);

        // Check the contact
        com.github.sparkmuse.swagger.starter.properties.Contact contact = apiInfo.getContact();
        if (contact != null) {
            MutableContact resultContact = new MutableContact();
            resultContact.name(contact.getName() == null ? DEFAULT_CONTACT.getName() : contact.getName());
            resultContact.name(contact.getEmail() == null ? DEFAULT_CONTACT.getEmail() : contact.getEmail());
            resultContact.name(contact.getUrl() == null ? DEFAULT_CONTACT.getUrl() : contact.getUrl());
            result.contact(resultContact);
        }

        return MutableApiInfo.toApiInfo(result);
    }

    private MutableApiInfo getMutableApiInfo(com.github.sparkmuse.swagger.starter.properties.ApiInfo apiInfo) {

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
package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Oauth;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import com.github.sparkmuse.swagger.starter.security.properties.TokenEndPoint;
import com.github.sparkmuse.swagger.starter.security.properties.TokenRequestEndPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.TokenEndpointBuilder;
import springfox.documentation.builders.TokenRequestEndpointBuilder;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OAuthSecurityProvider implements SecurityProvider {

    private static final String NAME = "Oauth";

    @Override
    public Optional<SecurityScheme> getScheme(Security security) {

        if (security == null || security.getOauth() == null) {
            return Optional.empty();

        }

        Oauth oauth = security.getOauth();

        OAuth schema = new OAuth(NAME, scopes(oauth.getScopes()), grantTypes(oauth));

        return Optional.of(schema);
    }

    private static List<GrantType> grantTypes(Oauth oauth) {

        TokenRequestEndPoint tokenRequest = oauth.getTokenRequest();

        Assert.notNull(tokenRequest, "Token request cannot be null");
        Assert.notNull(tokenRequest.getUrl(), "Token request url cannot be null");
        Assert.notNull(tokenRequest.getClientIdName(), "Token request client id cannot be null");
        Assert.notNull(tokenRequest.getClientSecretName(), "Token request secret cannot be null");

        TokenRequestEndpoint tokenRequestEndPoint = new TokenRequestEndpointBuilder()
                .url(tokenRequest.getUrl())
                .clientIdName(tokenRequest.getClientIdName())
                .clientSecretName(tokenRequest.getClientSecretName())
                .build();

        TokenEndPoint token = oauth.getToken();
        Assert.notNull(token, "Token endpoint cannot be null");
        Assert.notNull(token.getUrl(), "Token endpoint url cannot be null");
        Assert.notNull(token.getTokenName(), "Token endpoint name cannot be null");

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
                .map(scope -> new AuthorizationScope(scope, scope + " operation"))
                .collect(Collectors.toList());
    }
}

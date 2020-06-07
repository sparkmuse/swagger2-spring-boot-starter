package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Oauth;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import com.github.sparkmuse.swagger.starter.security.properties.TokenEndPoint;
import com.github.sparkmuse.swagger.starter.security.properties.TokenRequestEndPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

import static org.assertj.core.api.Assertions.assertThat;

class OAuthSecurityProviderTest {

    private OAuthSecurityProvider provider;

    @BeforeEach
    void setUp() {
        provider = new OAuthSecurityProvider();
    }

    @Nested
    @DisplayName("returns optional empty")
    class Empty {

        @Test
        @DisplayName("when security is null")
        void nullSecurity() {

            Optional<SecurityScheme> actual = provider.getScheme(null);

            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("when security api is null")
        void nullSecurityApi() {

            Security security = new Security();
            security.setOauth(null);

            Optional<SecurityScheme> actual = provider.getScheme(security);

            assertThat(actual).isEmpty();
        }
    }

    @Test
    @DisplayName("returns security schema when valid")
    void valid() {

        OAuth expected = getOauth();

        Security security = getSecurity();

        Optional<SecurityScheme> actual = provider.getScheme(security);

        assertThat(actual.get())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Security getSecurity() {
        Oauth oauth = new Oauth();
        oauth.setScopes(List.of("read"));
        TokenEndPoint token = new TokenEndPoint();
        token.setTokenName("token-name");
        token.setUrl("/token");
        oauth.setToken(token);
        TokenRequestEndPoint tokenRequest = new TokenRequestEndPoint();
        tokenRequest.setUrl("/authorize");
        tokenRequest.setClientIdName("client-id");
        tokenRequest.setClientSecretName("client-secret");
        oauth.setTokenRequest(tokenRequest);

        Security security = new Security();
        security.setOauth(oauth);
        return security;
    }

    private OAuth getOauth() {
        AuthorizationScope scope = new AuthorizationScope("read", "read operation");
        TokenRequestEndpoint tokenRequestEndPoint = new TokenRequestEndpointBuilder()
                .url("/authorize")
                .clientIdName("client-id")
                .clientSecretName("client-secret")
                .build();
        TokenEndpoint tokenEndPoint = new TokenEndpointBuilder()
                .url("/token")
                .tokenName("token-name")
                .build();
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenRequestEndpoint(tokenRequestEndPoint)
                .tokenEndpoint(tokenEndPoint)
                .build();
        return new OAuth("Oauth", List.of(scope), List.of(grantType));
    }
}
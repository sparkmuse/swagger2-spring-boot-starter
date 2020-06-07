package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Api;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ApiSecurityProviderTest {

    private ApiSecurityProvider provider;

    @BeforeEach
    void setUp() {
        provider = new ApiSecurityProvider();
    }

    @Test
    @DisplayName("returns optional schema when security is valid")
    void apiSchema() {

        SecurityScheme expected = new ApiKey("Api", "api-key", ApiKeyVehicle.HEADER.getValue());

        Api api = new Api();
        api.setKeyName("api-key");
        api.setVehicle(ApiKeyVehicle.HEADER);

        Security security = new Security();
        security.setApi(api);

        Optional<SecurityScheme> actual = provider.getScheme(security);

        assertThat(actual)
                .usingFieldByFieldValueComparator()
                .contains(expected);
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
            security.setApi(null);

            Optional<SecurityScheme> actual = provider.getScheme(security);

            assertThat(actual).isEmpty();
        }
    }
}
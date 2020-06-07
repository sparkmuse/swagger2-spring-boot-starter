package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BasicSecurityProviderTest {

    private BasicSecurityProvider provider;

    @BeforeEach
    void setUp() {
        provider = new BasicSecurityProvider();
    }

    @Test
    @DisplayName("returns basic security schema when true")
    void basicTrue() {

        Security security = new Security();
        security.setBasic(true);

        SecurityScheme expected = new BasicAuth("Basic");

        Optional<SecurityScheme> actual = provider.getScheme(security);

        assertThat(actual)
                .usingFieldByFieldValueComparator()
                .contains(expected);
    }

    @Nested
    @DisplayName("returns empty security schema")
    class Empty {

        @Test
        @DisplayName("when false")
        void basicFalse() {

            Security security = new Security();
            security.setBasic(false);

            Optional<SecurityScheme> actual = provider.getScheme(security);

            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("when null security")
        void nullSecurity() {

            Optional<SecurityScheme> actual = provider.getScheme(null);

            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("when null basic")
        void nullBasic() {

            Security security = new Security();
            security.setBasic(null);

            Optional<SecurityScheme> actual = provider.getScheme(security);

            assertThat(actual).isEmpty();
        }
    }
}
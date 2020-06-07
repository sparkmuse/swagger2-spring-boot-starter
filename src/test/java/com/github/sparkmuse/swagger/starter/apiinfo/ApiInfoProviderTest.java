package com.github.sparkmuse.swagger.starter.apiinfo;

import com.github.sparkmuse.swagger.starter.contact.ContactProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiInfoProviderTest {

    private ApiInfoProvider apiInfoProvider;

    @Mock
    private ContactProvider contactProvider;

    @BeforeEach
    void setUp() {
        apiInfoProvider = new ApiInfoProvider(contactProvider);
    }

    @Test
    @DisplayName("gets defaults when everything is null")
    void allDefaults() {

        Contact contact = new Contact("Contact name", "contact.url", "contact@email.com");
        ApiInfo expected = new ApiInfo(
                "Api Documentation",
                "Api Documentation",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());

        ApiInfoProperties apiInfoProperties = new ApiInfoProperties();

        when(contactProvider.get(any())).thenReturn(contact);

        ApiInfo actual = apiInfoProvider.get(apiInfoProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("gets defaults when null")
    void allDefaultsWhenNull() {

        Contact contact = new Contact("Contact name", "contact.url", "contact@email.com");
        ApiInfo expected = new ApiInfo(
                "Api Documentation",
                "Api Documentation",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());

        ApiInfo actual = apiInfoProvider.get(null);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("gets defaults replacing nulls with defaults")
    void someDefaults() {

        Contact contact = new Contact("Contact name", "contact.url", "contact@email.com");
        ApiInfo expected = new ApiInfo(
                "New title",
                "Api Documentation",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());

        ApiInfoProperties apiInfoProperties = new ApiInfoProperties();
        apiInfoProperties.setTitle("New title");

        when(contactProvider.get(any())).thenReturn(contact);

        ApiInfo actual = apiInfoProvider.get(apiInfoProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }
}
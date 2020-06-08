package com.github.sparkmuse.swagger.starter.apiinfo;

import com.github.sparkmuse.swagger.starter.contact.ContactProperties;
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

    private static final Contact DEFAULT_CONTACT = new Contact("Contact name", "contact.url", "contact@email.com");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Api Documentation",
            "Api Documentation",
            "1.0",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>());
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

        ApiInfoProperties apiInfoProperties = new ApiInfoProperties();

        when(contactProvider.get(any())).thenReturn(DEFAULT_CONTACT);

        ApiInfo actual = apiInfoProvider.get(apiInfoProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(DEFAULT_API_INFO);
    }

    @Test
    @DisplayName("gets defaults when null")
    void allDefaultsWhenNull() {

        ApiInfo actual = apiInfoProvider.get(null);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(DEFAULT_API_INFO);
    }

    @Test
    @DisplayName("gets defaults replacing nulls with defaults")
    void someDefaults() {

        Contact contact = DEFAULT_CONTACT;
        ApiInfo expected = new ApiInfo(
                "New title",
                "New description",
                "New version",
                "url",
                contact,
                "licence",
                "url",
                new ArrayList<>());

        ApiInfoProperties apiInfoProperties = new ApiInfoProperties();
        apiInfoProperties.setTitle("New title");
        apiInfoProperties.setDescription("New description");
        apiInfoProperties.setVersion("New version");
        apiInfoProperties.setTermsOfServiceUrl("url");
        apiInfoProperties.setLicense("licence");
        apiInfoProperties.setLicenseUrl("url");

        when(contactProvider.get(any())).thenReturn(contact);

        ApiInfo actual = apiInfoProvider.get(apiInfoProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }
}
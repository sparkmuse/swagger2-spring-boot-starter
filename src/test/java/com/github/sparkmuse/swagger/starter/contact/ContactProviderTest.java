package com.github.sparkmuse.swagger.starter.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springfox.documentation.service.Contact;

import static org.assertj.core.api.Assertions.assertThat;

class ContactProviderTest {

    private static final Contact DEFAULT_CONTACT =
            new Contact("Contact name", "contact.url", "contact@email.com");

    private ContactProvider contactProvider;

    @BeforeEach
    void setUp() {
        contactProvider = new ContactProvider();
    }

    @Test
    @DisplayName("gets defaults when everything is null")
    void allDefaults() {

        ContactProperties contactProperties = new ContactProperties();

        Contact actual = contactProvider.get(contactProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(DEFAULT_CONTACT);
    }

    @Test
    @DisplayName("gets defaults when null")
    void allDefaultsWhenNull() {

        Contact actual = contactProvider.get(null);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(DEFAULT_CONTACT);
    }

    @Test
    @DisplayName("gets defaults replacing nulls with defaults")
    void someDefaults() {

        Contact expected = new Contact("New name", "url", "email");

        ContactProperties contactProperties = new ContactProperties();
        contactProperties.setName("New name");
        contactProperties.setUrl("url");
        contactProperties.setEmail("email");

        Contact actual = contactProvider.get(contactProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }
}
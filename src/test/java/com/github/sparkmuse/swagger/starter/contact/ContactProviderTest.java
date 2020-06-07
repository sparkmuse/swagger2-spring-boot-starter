package com.github.sparkmuse.swagger.starter.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springfox.documentation.service.Contact;

import static org.assertj.core.api.Assertions.assertThat;

class ContactProviderTest {

    private ContactProvider contactProvider;

    @BeforeEach
    void setUp() {
        contactProvider = new ContactProvider();
    }

    @Test
    @DisplayName("gets defaults when everything is null")
    void allDefaults() {

        Contact expected = new Contact("Contact name", "contact.url", "contact@email.com");

        ContactProperties contactProperties = new ContactProperties();

        Contact actual = contactProvider.get(contactProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("gets defaults when null")
    void allDefaultsWhenNull() {

        Contact expected = new Contact("Contact name", "contact.url", "contact@email.com");

        Contact actual = contactProvider.get(null);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("gets defaults replacing nulls with defaults")
    void someDefaults() {

        Contact expected = new Contact("New name", "contact.url", "contact@email.com");

        ContactProperties contactProperties = new ContactProperties();
        contactProperties.setName("New name");

        Contact actual = contactProvider.get(contactProperties);

        assertThat(actual)
                .usingRecursiveComparison()
                .usingDefaultComparator()
                .isEqualTo(expected);
    }
}
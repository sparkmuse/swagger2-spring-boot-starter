package com.github.sparkmuse.swagger.starter.contact;

import org.springframework.stereotype.Component;
import springfox.documentation.service.Contact;

@Component
public class ContactProvider {

    private final static Contact DEFAULT_CONTACT =
            new Contact("Contact name", "contact.url", "contact@email.com");

    public Contact get(ContactProperties contactProperties) {

        if (contactProperties == null) {
            return DEFAULT_CONTACT;
        }

        String name = contactProperties.getName() == null ? DEFAULT_CONTACT.getName() : contactProperties.getName();
        String url = contactProperties.getUrl() == null ? DEFAULT_CONTACT.getUrl() : contactProperties.getUrl();
        String email = contactProperties.getEmail() == null ? DEFAULT_CONTACT.getEmail() : contactProperties.getEmail();

        return new Contact(name, url, email);
    }
}

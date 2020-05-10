package com.github.sparkmuse.swagger.starter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import springfox.documentation.service.Contact;

@Data
@NoArgsConstructor
@Accessors(fluent = true)
public class MutableContact {

    private String name;
    private String url;
    private String email;

    public static Contact toContact(MutableContact mutableContact) {
        return new Contact(
                mutableContact.name(),
                mutableContact.email(),
                mutableContact.url());
    }
}

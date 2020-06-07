package com.github.sparkmuse.swagger.starter.contact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactProperties {
    private String name;
    private String url;
    private String email;
}

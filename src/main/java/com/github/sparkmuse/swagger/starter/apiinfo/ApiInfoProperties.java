package com.github.sparkmuse.swagger.starter.apiinfo;

import com.github.sparkmuse.swagger.starter.contact.ContactProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@NoArgsConstructor
public class ApiInfoProperties {
    private String version;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;

    @NestedConfigurationProperty
    private ContactProperties contact;
}

package com.github.sparkmuse.swagger.starter.apiinfo;

import com.github.sparkmuse.swagger.starter.contact.ContactProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ApiInfoProvider {

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Api Documentation",
            "Api Documentation",
            "1.0",
            "urn:tos",
            new Contact("Contact name", "contact.url", "contact@email.com"),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>());

    private final ContactProvider contactProvider;

    public ApiInfo get(ApiInfoProperties apiInfo) {

        if (apiInfo == null) {
            return DEFAULT_API_INFO;
        }

        Contact contact = contactProvider.get(apiInfo.getContact());

        String title = apiInfo.getTitle() == null ? DEFAULT_API_INFO.getTitle() : apiInfo.getTitle();
        String description = apiInfo.getDescription() == null ? DEFAULT_API_INFO.getDescription() : apiInfo.getDescription();
        String version = apiInfo.getVersion() == null ? DEFAULT_API_INFO.getVersion() : apiInfo.getVersion();
        String termsUrl = apiInfo.getTermsOfServiceUrl() == null ? DEFAULT_API_INFO.getTermsOfServiceUrl() : apiInfo.getTermsOfServiceUrl();
        String license = apiInfo.getLicense() == null ? DEFAULT_API_INFO.getLicense() : apiInfo.getLicense();
        String licenseUrl = apiInfo.getLicenseUrl() == null ? DEFAULT_API_INFO.getLicenseUrl() : apiInfo.getLicenseUrl();

        return new ApiInfo(title, description, version, termsUrl, contact, license, licenseUrl, new ArrayList<>());
    }
}

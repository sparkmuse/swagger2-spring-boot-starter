package com.github.sparkmuse.swagger.starter;

import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MutableApiInfo {

    private String version;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private MutableContact contact;
    private List<VendorExtension> vendorExtensions;


    public static ApiInfo toApiInfo(MutableApiInfo mutableApiInfo) {

        return new ApiInfo(mutableApiInfo.getTitle(),
                mutableApiInfo.getDescription(),
                mutableApiInfo.getVersion(),
                mutableApiInfo.getTermsOfServiceUrl(),
                MutableContact.toContact(mutableApiInfo.getContact()),
                mutableApiInfo.getLicense(),
                mutableApiInfo.getLicenseUrl(),
                mutableApiInfo.getVendorExtensions());

    }
}

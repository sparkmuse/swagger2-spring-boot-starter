package com.github.sparkmuse.swagger.starter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to mutate the Api info as needed
 */

@Data
@NoArgsConstructor
@Accessors(fluent = true)
public class MutableApiInfo {

    private String version;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private MutableContact contact;
    private List<VendorExtension> vendorExtensions = new ArrayList<>();


    /**
     * Converts from a MutableApiInfo to an ApiInfo
     *
     * @param mutableApiInfo The source for the conversion
     * @return Converted ApiInfo
     */
    public static ApiInfo toApiInfo(MutableApiInfo mutableApiInfo) {

        return new ApiInfo(mutableApiInfo.title(),
                mutableApiInfo.description(),
                mutableApiInfo.version(),
                mutableApiInfo.termsOfServiceUrl(),
                MutableContact.toContact(mutableApiInfo.contact()),
                mutableApiInfo.license(),
                mutableApiInfo.licenseUrl(),
                mutableApiInfo.vendorExtensions());
    }
}

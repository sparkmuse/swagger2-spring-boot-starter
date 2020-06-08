package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Api;
import com.github.sparkmuse.swagger.starter.security.properties.Security;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;

import java.util.Optional;

@Component
public class ApiSecurityProvider implements SecurityProvider {

    private static final String NAME = "Api";

    @Override
    public Optional<SecurityScheme> getScheme(Security security) {

        if (security == null || security.getApi() == null) {
            return Optional.empty();
        }

        Api api = security.getApi();

        Assert.notNull(api.getKeyName(), "Api keyName cannot be null");
        Assert.notNull(api.getVehicle(), "Api vehicle cannot be null");

        ApiKey schema = new ApiKey(NAME, api.getKeyName(), api.getVehicle().getValue());
        return Optional.of(schema);
    }
}

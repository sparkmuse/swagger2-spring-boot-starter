package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Security;
import org.springframework.stereotype.Component;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;

import java.util.Optional;

@Component
public class BasicSecurityProvider implements SecurityProvider {

    private static final String NAME = "Basic";

    @Override
    public Optional<SecurityScheme> getScheme(Security security) {

        if (security == null || security.getBasic() == null) {
            return Optional.empty();
        }

        return Boolean.TRUE.equals(security.getBasic()) ? Optional.of(new BasicAuth(NAME)) : Optional.empty();

    }
}
package com.github.sparkmuse.swagger.starter.security;

import com.github.sparkmuse.swagger.starter.security.properties.Security;
import springfox.documentation.service.SecurityScheme;

import java.util.Optional;

public interface SecurityProvider {
    Optional<SecurityScheme> getScheme(Security security);
}

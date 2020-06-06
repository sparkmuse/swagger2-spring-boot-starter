package com.github.sparkmuse.swagger.starter.properties.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.swagger.web.ApiKeyVehicle;

@Data
@NoArgsConstructor
public class Api {
    private String keyName;
    private ApiKeyVehicle vehicle;
}

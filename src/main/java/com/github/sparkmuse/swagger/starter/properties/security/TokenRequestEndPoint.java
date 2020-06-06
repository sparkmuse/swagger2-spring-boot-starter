package com.github.sparkmuse.swagger.starter.properties.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenRequestEndPoint {
    private String url;
    private String clientIdName;
    private String clientSecretName;
}

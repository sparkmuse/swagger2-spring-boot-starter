package com.github.sparkmuse.swagger.starter.security.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenRequestEndPoint {
    private String url;
    private String clientIdName;
    private String clientSecretName;
}

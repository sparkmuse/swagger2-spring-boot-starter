package com.github.sparkmuse.swagger.starter.security.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenEndPoint {
    private String url;
    private String tokenName;
}

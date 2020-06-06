package com.github.sparkmuse.swagger.starter.properties.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenEndPoint {
    private String url;
    private String tokenName;
}

package com.github.sparkmuse.swagger.starter.properties.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Oauth {
    private TokenRequestEndPoint tokenRequest;
    private TokenEndPoint token;
    private List<String> scopes = new ArrayList<>();
}

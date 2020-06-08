package org.linn.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Oauth2Properties {

    private String jwtSigningKey = "linn";
}

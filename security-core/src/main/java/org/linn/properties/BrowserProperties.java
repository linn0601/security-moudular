package org.linn.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrowserProperties {

    private String loginPage = "/login.html";

    private LoginType loginType = LoginType.JSON;

}

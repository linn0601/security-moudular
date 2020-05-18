package org.linn.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "custom.security")
@Setter
@Getter
@Configuration
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();


}

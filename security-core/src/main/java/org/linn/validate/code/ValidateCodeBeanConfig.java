package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ValidateCodeBeanConfig {

    private final SecurityProperties securityProperties;

    /**
     * 容器启动时查找是否有imageCodeGenerator的Bean，如果有就不会使用下面的Bean
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        return new ImageCodeGenerator(securityProperties);
    }
}

package org.linn;

import lombok.RequiredArgsConstructor;
import org.linn.logout.CustomLogoutSuccessHandler;
import org.linn.properties.SecurityProperties;
import org.linn.session.CustomExpiredSessionStrategy;
import org.linn.session.CustomInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowserSecurityBeanConfig {

    private final SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new CustomInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new CustomExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(LogoutHandler.class)
    public LogoutSuccessHandler logoutHandler(){
        return new CustomLogoutSuccessHandler(securityProperties.getBrowser().getLoginOutUrl());
    }
}

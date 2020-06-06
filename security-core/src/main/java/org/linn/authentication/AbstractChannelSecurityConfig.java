package org.linn.authentication;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Autowired
    protected AuthenticationSuccessHandler successHandler;
    @Autowired
    protected AuthenticationFailureHandler failureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception{
        http.formLogin()
                //登录页面请求url
                .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
                //登录接口请求url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);
    }*/
}

package org.linn;

import org.linn.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.linn.constants.SecurityConstants;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.linn.constants.SecurityConstants.*;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler successHandler;
    @Autowired
    protected AuthenticationFailureHandler failureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //登录页面请求url
                .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
                //登录接口请求url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);
        // ==========================================================================================

        //应用多个SecurityConfig配置
        http //.apply(validateCodeSecurityConfig)
                // .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers(
                        DEFAULT_UN_AUTHENTICATION_URL,
                        DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getRegisterPage(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        "/oauth/authorize"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}

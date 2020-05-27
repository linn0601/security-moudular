package org.linn.config;

import lombok.RequiredArgsConstructor;
import org.linn.authentication.AbstractChannelSecurityConfig;
import org.linn.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.linn.properties.SecurityProperties;
import org.linn.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import static org.linn.constants.SecurityConstants.*;

/**
 * @ EnableWebSecurity 不需要加入，SecurityAutoConfiguration中已经被表示
 * <p>
 * WebSecurityConfigAdapter就是用来创建过滤器链的
 * 配置器中创建了大量的过滤器，在启动时最终在HttpSecurity建造SecurityFilterChain实例放入过滤器链中
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    private final SecurityProperties securityProperties;

    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    /**
     * 对 security配置的补充配置
     */
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    private final ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 用来配置HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);
        // ==========================================================================================

        //应用多个SecurityConfig配置
        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberSecond()).userDetailsService(userDetailsService)
                .and()
                .sessionManagement().invalidSessionUrl("/session/invalid")
                .and()
                .authorizeRequests()
                .antMatchers(
                        DEFAULT_UN_AUTHENTICATION_URL,
                        DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getRegisterPage()
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 记住我功能
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //启动时创建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}

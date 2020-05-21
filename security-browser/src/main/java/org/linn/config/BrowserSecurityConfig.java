package org.linn.config;

import lombok.RequiredArgsConstructor;
import org.linn.authentication.CustomFailureHandler;
import org.linn.authentication.CustomSuccessHandler;
import org.linn.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.linn.properties.SecurityProperties;
import org.linn.validate.code.SmsCodeFilter;
import org.linn.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @ EnableWebSecurity 不需要加入，SecurityAutoConfiguration中已经被表示
 * <p>
 * WebSecurityConfigAdapter就是用来创建过滤器链的
 * 配置器中创建了大量的过滤器，在启动时最终在HttpSecurity建造SecurityFilterChain实例放入过滤器链中
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityProperties securityProperties;
    private final CustomSuccessHandler successHandler;
    private final CustomFailureHandler failureHandler;
    private final ValidateCodeFilter validateCodeFilter;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final SmsCodeFilter smsCodeFilter;
    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

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

    /**
     * 用来配置HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberSecond()).userDetailsService(userDetailsService)
                .and()
                .authorizeRequests().antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "/code/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                //应用多个SecurityConfig配置
                .apply(smsCodeAuthenticationSecurityConfig);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

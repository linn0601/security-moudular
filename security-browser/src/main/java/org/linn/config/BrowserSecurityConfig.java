package org.linn.config;

import lombok.RequiredArgsConstructor;
import org.linn.authentication.CustomFailureHandler;
import org.linn.authentication.CustomSuccessHandler;
import org.linn.properties.SecurityProperties;
import org.linn.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    /**
     * 用来配置HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .authorizeRequests().antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "/code/image").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

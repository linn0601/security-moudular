package org.linn.authentication.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    /**
     * 进行身份认证逻辑
     * @param authentication 封装的用户信息对象 {@link SmsCodeAuthenticationToken }
     * @return 认证成功返回 {@link Authentication} 的封装
     * @throws AuthenticationException 认证失败时抛出的异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());
        Optional.ofNullable(user).orElseThrow(() ->
                new InternalAuthenticationServiceException("无法获取用户信息"));

        //如果获取到用户信息，那么重新构造一个SmsCodeAuthenticationToken

        // ======================================================================
        //传入用户信息和用户权限
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,
                user.getAuthorities());
        //Copy before userDetails
        authenticationResult.setDetails(smsCodeAuthenticationToken.getDetails());
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //判断传进来的authentication 是不是SmsAuthentication
        return (SmsCodeAuthenticationToken.class).isAssignableFrom(authentication);
    }
}

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

/**
 * 进行身份认证
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    /**
     * (non-Javadoc)
     *
     * @param authentication 封装的用户信息对象 {@link SmsCodeAuthenticationToken }
     * @see AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        String principal = (String) smsCodeAuthenticationToken.getPrincipal();
        UserDetails user = userDetailsService.loadUserByUsername(principal);
        Optional.ofNullable(user).orElseThrow(() ->
                new InternalAuthenticationServiceException("无法获取用户信息"));

        //如果获取到用户信息，那么重新构造一个SmsCodeAuthenticationToken
        //传入用户信息和用户权限
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,
                user.getAuthorities());
        //Copy before userDetails
        authenticationResult.setDetails(smsCodeAuthenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * (non-Javadoc)
     *
     * @see AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //判断传进来的authentication 是不是SmsAuthentication
        return (SmsCodeAuthenticationToken.class).isAssignableFrom(authentication);
    }
}

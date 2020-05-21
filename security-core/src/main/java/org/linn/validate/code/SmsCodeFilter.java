package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 验证验证码过滤器
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final HttpSession httpSession;
    private final Set<String> url = new HashSet<>();
    private final SecurityProperties securityProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] urls = StringUtils.splitByWholeSeparator(securityProperties.getCode().getSmsCode().getUrl(), ",");
       if (urls != null){
           url.addAll(Arrays.asList(urls));
       }
       //拦截请求
        url.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //指定过滤器在登陆时才会生效
        if (url.stream().anyMatch(a -> antPathMatcher.match(a, request.getRequestURI()))) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                //捕获到验证码验证验证异常使用自定义登录失败处理类
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ValidateCode codeInSession = (ValidateCode) httpSession.getAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpire()) {
            httpSession.removeAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        httpSession.removeAttribute(ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
    }
}

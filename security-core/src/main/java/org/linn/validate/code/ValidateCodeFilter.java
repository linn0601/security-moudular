package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.linn.constants.SecurityConstants;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component("validateCodeFilter")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证失败处理器
     */
    private final AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 系统配置信息
     */
    private final SecurityProperties securityProperties;

    /**
     * 匹配url工具类
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 系统中验证码处理器
     */
    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有需要校验验证码的url
     */
    private final Map<String, ValidateCodeType> urlMap = new HashMap<>();


    /**
     * 初始化拦要拦截的url配置信息 （包含在配置文件中读取道德）
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSmsCode().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 根据配置的url 存储 map中
     *
     * @param urlString 读取到的默认或者配置文件中配置的
     * @param type      类型
     */
    private void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparator(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    /**
     * (non-Javadoc)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求校验的类型
        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            log.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type);
                log.info("验证码校验通过");
                //validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                //捕获到验证码验证验证异常使用自定义登录失败处理类
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型 IMAGE | SMS {@link ValidateCodeType}
     *
     * @param request 当前请求
     * @return IMAGE | SMS {@link ValidateCodeType}
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "GET")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

}

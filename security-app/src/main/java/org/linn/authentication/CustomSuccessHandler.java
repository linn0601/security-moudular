package org.linn.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.linn.properties.LoginType;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    private final BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(" 校验通过,登录成功! ");
        //对请求进行封装成 UsernamePasswordAuthenticationToken对象
        UsernamePasswordAuthenticationToken authRequest = authenticationConverter.convert(request);
        if (null == authRequest) {
            throw new UnapprovedClientAuthenticationException("请求头中无Client信息");
        }
        String clientId =  authRequest.getPrincipal().toString();
        String clientSecurity = authRequest.getCredentials().toString();

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (null == clientDetails) {
            throw new UnapprovedClientAuthenticationException("ClientId对应信息不存在:" + clientId);
        } else if (!passwordEncoder.matches(clientSecurity,clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("ClientSecurity不匹配");
        }
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = defaultAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(token));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}

package org.linn.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.linn.entity.JsonResult;
import org.linn.properties.LoginType;
import org.linn.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 失败处理器
 */
@Component("failureHandler")
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(JsonResult.error(401, e.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, e);
        }

    }
}

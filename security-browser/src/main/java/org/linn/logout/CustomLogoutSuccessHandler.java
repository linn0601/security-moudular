package org.linn.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.linn.entity.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String loginOutUrl;

    public CustomLogoutSuccessHandler(String loginOutUrl) {
        this.loginOutUrl = loginOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (StringUtils.isNotBlank(this.loginOutUrl)){
            response.sendRedirect(this.loginOutUrl);
        }else {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/ json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(JsonResult.ok(200, "退出成功")));
        }
    }
}

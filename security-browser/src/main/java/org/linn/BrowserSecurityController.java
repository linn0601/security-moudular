package org.linn;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.linn.constants.SecurityConstants;
import org.linn.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowserSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final SecurityProperties securityProperties;

    @GetMapping(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
    public ResponseEntity<Map<String, String>> requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String target = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(target, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new ResponseEntity<Map<String, String>>(new HashMap<String, String>() {
            {
                put("content", "访问服务需要认证，请登录!");
            }
        }, HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("session/invalid")
    public ResponseEntity<Map<String, String>> sessionInvalid() {
        String message = "session 失效";
        return new ResponseEntity<Map<String, String>>(new HashMap<String, String>() {
            {
                put("content", message);
            }
        }, HttpStatus.UNAUTHORIZED);
    }
}

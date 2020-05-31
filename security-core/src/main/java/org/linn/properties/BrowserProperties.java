package org.linn.properties;

import lombok.Getter;
import lombok.Setter;
import org.linn.constants.SecurityConstants;

@Setter
@Getter
public class BrowserProperties {

    /**
     * 默认登录页面
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 默认注册页面
     */
    private String registerPage = SecurityConstants.DEFAULT_REGISTER_PAGE_URL;

    /**
     * 数据类型
     * 返回 JSON
     * 重定向 REDIRECT
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * 记住我的过期时间
     */
    private int rememberSecond = 360000;

    private SessionProperties session = new SessionProperties();

}

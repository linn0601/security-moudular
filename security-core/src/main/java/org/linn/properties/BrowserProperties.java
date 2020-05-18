package org.linn.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrowserProperties {

    //登录页面
    private String loginPage = "/login.html";

    /**
     * 数据类型
     * 返回 JSON
     * 重定向 REDIRECT
     */
    private LoginType loginType = LoginType.JSON;

}

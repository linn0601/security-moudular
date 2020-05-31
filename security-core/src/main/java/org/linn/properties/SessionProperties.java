package org.linn.properties;

import lombok.Getter;
import lombok.Setter;
import org.linn.constants.SecurityConstants;

@Setter
@Getter
public class SessionProperties {

    /**
     * 同一个系统里最大的session数
     */
    private int maximumSessions = 1;

    /**
     * 达到最大session时是否组织新的登录请求
     */

    private boolean maxSessionsPreventsLogin = false;

    /**
     * session失效跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
}

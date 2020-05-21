package org.linn.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;

    /**
     * 验证码url
     */
    private String url = "";
}

package org.linn.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        this.setLength(4);
    }

    /**
     * 验证码宽度
     */
    private int width = 67;
    /**
     * 验证码高度
     */
    private int height = 23;
    /**
     * 有效时间
     */
    private int expireIn = 60;
    /**
     * 干扰项
     */
    private int circleCount = 0;

    /**
     * 需要校验的url
     */
    private String url;
}

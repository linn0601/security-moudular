package org.linn.validate.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ValidateCode {

    /**
     * 短信验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }
    //判断验证码是否过期
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}

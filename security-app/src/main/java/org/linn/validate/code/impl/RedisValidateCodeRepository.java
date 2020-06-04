package org.linn.validate.code.impl;

import org.apache.commons.lang3.StringUtils;
import org.linn.validate.code.ValidateCode;
import org.linn.validate.code.ValidateCodeException;
import org.linn.validate.code.ValidateCodeRepository;
import org.linn.validate.code.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * (no-Javadoc)
     * org.linn.validate.code.ValidateCodeRepository#save
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        //设备ID
        String key = this.generate(request, type);
        redisTemplate.opsForValue().set(key, code, 600, TimeUnit.SECONDS);
    }

    /**
     * 生成验证码的key
     * <p>
     * desc : request Header must include param "deviceId"
     */
    private String generate(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请求头中必须带上 deviceId 参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

    /**
     * (no-Javadoc)
     *
     * @see ValidateCodeRepository#get
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object key = redisTemplate.opsForValue().get(this.generate(request, type));
        return (ValidateCode) Optional.ofNullable(key).orElse(null);
    }

    /**
     * (no-Javadoc)
     *
     * @see ValidateCodeRepository#remove
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(this.generate(request, type));
    }
}

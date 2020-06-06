package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import org.linn.authentication.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ValidateCodeProcessorHolder {

    private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return this.findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * 返回ValidateCodeProcessor 接口的实现
     *
     * @param type {@link ValidateCodeType 枚举类}
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        Optional.ofNullable(processor).orElseThrow(() ->
                new ValidateCodeException("验证码处理器 " + name + "不存在"));
        return processor;
    }
}

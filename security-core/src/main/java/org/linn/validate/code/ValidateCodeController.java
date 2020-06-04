package org.linn.validate.code;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ValidateCodeController {

    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 控制层| 根据前端请求返回
     * @param type 验证码的类型
     * 默认实现如下
     * @see org.linn.constants.SecurityConstants
     * 定制化配置实现如下
     * @see ValidateCodeType
     *
     * 在请求头中加入 deviceId
     */
    @GetMapping("/code/{type}")
    public void createCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

}

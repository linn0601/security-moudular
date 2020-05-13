package org.linn.handler;

import lombok.extern.slf4j.Slf4j;
import org.linn.bean.JsonResult;
import org.linn.exception.CustomException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.StringJoiner;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public Map<String, Object> customException(CustomException e) {
        log.error(e.getMessage(), e);
        return JsonResult.error(500, e.getMessage());
    }

    @ExceptionHandler({BindException.class})
    public JsonResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        return JsonResult.error(500, fieldError.getDefaultMessage());
    }

    /**
     * 自定义方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getMessage(),e);
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return JsonResult.error(500,sj.toString());
    }

}

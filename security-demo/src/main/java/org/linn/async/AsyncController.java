package org.linn.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * 异步处理
 */
@RestController
@Slf4j
public class AsyncController {

    @GetMapping("/async")
    public String order() throws Exception {
        //log.info("主线程开始：" + message.currentTimeMillis());
        Callable<String> result = () -> {
            log.info("子线程开始： " );
            Thread.sleep(2000);
            log.info("子线程结束");
            return "操作成功";
        };
        //log.info("结束请求");
        return result.call();
    }

}

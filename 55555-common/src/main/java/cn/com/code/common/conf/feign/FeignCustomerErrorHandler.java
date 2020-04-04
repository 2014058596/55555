package cn.com.code.common.conf.feign;


import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import static feign.FeignException.errorStatus;

/**
* @ClassName: FeignCustomerErrorHandler
* @Description: 异常解析器 使用这个方式，需要在熔断器关闭时才起作用，因为它们的执行过程是，先走这个拦截器，再走熔断的fallback，所以这个异常会被熔断吞掉，返回状态为200，返回值为你的fallback的默认值。
* @author: 55555
* @date: 2020年04月04日 10:25 下午
*/
@Component
@Log4j2
@ConditionalOnClass(ErrorDecoder.class)
public class FeignCustomerErrorHandler implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        log.info("feign client response:", response);
        return errorStatus(s, response);
    }
}

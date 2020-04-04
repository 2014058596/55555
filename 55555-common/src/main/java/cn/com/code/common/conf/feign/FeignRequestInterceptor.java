package cn.com.code.common.conf.feign;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;


/**
* @ClassName: FeignRequestInterceptor
* @Description: 请求拦截器
* @author: 55555
* @date: 2020年04月04日 10:24 下午
*/
@Component
@ConditionalOnClass(Feign.class)
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

    }
}

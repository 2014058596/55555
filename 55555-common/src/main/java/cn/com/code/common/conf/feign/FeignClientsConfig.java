package cn.com.code.common.conf.feign;

import feign.Feign;
import feign.Retryer;
import feign.querymap.BeanQueryMapEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 
* @ClassName: FeignClientsConfig
* @Description: TODO
* @author: 55555
* @date: 2020年04月04日 10:41 下午
*/
@Configuration
@ConditionalOnClass(Feign.class)
@EnableFeignClients("cn.com.code")
public class FeignClientsConfig {

    /**
     * 提供一个 BeanQueryMapEncoder 编码器
     *
     * @return
     */
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder().queryMapEncoder(new BeanQueryMapEncoder()).retryer(Retryer.NEVER_RETRY);
    }

}
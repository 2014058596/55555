package cn.com.code.admin.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: JacksonConf
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月21日 12:51 下午
 */
@Configuration
public class JacksonConf {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}

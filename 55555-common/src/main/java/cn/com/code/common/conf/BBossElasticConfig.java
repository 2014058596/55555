package cn.com.code.common.conf;

import org.frameworkset.elasticsearch.boot.BBossESProperties;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @program: GeoSocial
 * @description: bboss-elastic
 * @author: 韩玉坤
 * @create: 2019-09-16 10:57
 **/
@Configuration
@ConditionalOnClass(BBossESProperties.class)
public class BBossElasticConfig {

    /**
     * 默认es集群配置
     * @return
     */
    @Primary
    @Bean(initMethod = "start")
    @ConfigurationProperties("spring.elasticsearch.bboss.default")
    public BBossESStarter bbossESStarterDefault(){
        return new BBossESStarter();

    }
}

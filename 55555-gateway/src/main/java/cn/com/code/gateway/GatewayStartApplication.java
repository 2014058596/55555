package cn.com.code.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: GatewayStartApplication
 * @Description: 启动类
 * @author: 55555
 * @date: 2020年04月04日 5:53 下午
 */
@SpringBootApplication
@ComponentScan("cn.com.code")
@EnableDiscoveryClient
public class GatewayStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GatewayStartApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayStartApplication.class, args);
    }



}

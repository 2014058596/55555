package cn.com.code.system;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * @ClassName: SystemStartApplication
 * @Description: 启动类
 * @author: 55555
 * @date: 2020年04月15日 9:41 上午
 */
@SpringBootApplication
@EnableAdminServer
@ComponentScan("cn.com.code")
@EnableZipkinServer
public class SystemStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SystemStartApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SystemStartApplication.class, args);
    }


}

package cn.com.code.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Base64;

/**
 * @ClassName: AdminStartApplication
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月04日 5:53 下午
 */
@SpringBootApplication
@ComponentScan("cn.com.code")
@EnableDiscoveryClient
@MapperScan("cn.com.code.admin.mapper")
@EnableTransactionManagement
public class AdminStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AdminStartApplication.class);
    }

    public static void main(String[] args) {
        byte[] encode3 = Base64.getEncoder().encode("nanshan-test_MDAtMDAtMDAtMDAtMDAtMDA=_2099-04-10 00:00:00".getBytes());
        System.out.println(new String(encode3));
        SpringApplication.run(AdminStartApplication.class, args);
    }



}

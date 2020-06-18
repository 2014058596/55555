package cn.com.code.admin;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
@EnableCircuitBreaker
@Log4j2
public class AdminStartApplication extends SpringBootServletInitializer {

    static{
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            log.debug("----------------系统关闭-------------------");
        }));
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AdminStartApplication.class);
    }


    static String a;
    static String b;


    public static void main(String[] args) throws InterruptedException {
        //execute();

        SpringApplication.run(AdminStartApplication.class, args);

    }

    public static void execute() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat(prefix+"%d").setUncaughtExceptionHandler((thread, throwable)-> log.error("ThreadPool {} got exception", thread, throwable)).build());
        //提交10个任务到线程池处理，第5个任务会抛出运行时异常
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        }));



        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }



}

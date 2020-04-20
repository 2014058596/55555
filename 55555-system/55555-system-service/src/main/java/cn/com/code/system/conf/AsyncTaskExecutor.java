package cn.com.code.system.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: AsyncTaskExecutor
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月15日 10:28 下午
 */
@Configuration
public class AsyncTaskExecutor {

    @Bean({"taskExecutor", "webMvcAsyncTaskExecutor"})
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(80);
        executor.setMaxPoolSize(80);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}

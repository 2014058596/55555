package cn.com.code.common.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName: StarterHandler
 * @Description: 系统启动加载
 * @author: 55555
 * @date: 2020年04月04日 10:39 下午
 */
@Log4j2
@Component
public class StarterHandler implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

        log.debug("-----------系统启动成功-------------");
    }
}

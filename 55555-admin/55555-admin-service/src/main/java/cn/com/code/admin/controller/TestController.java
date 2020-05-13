package cn.com.code.admin.controller;

import cn.com.code.base.bean.CommonException;
import cn.com.code.base.bean.StandardResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月14日 11:07 下午
 */
@RestController
@Log4j2
@Api(tags="测试类")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/test")
    @HystrixCommand(
            fallbackMethod = "testFallbackMethod",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000" ),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1" )
            },
            ignoreExceptions = {
                CommonException.class
            })
    public String test(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug(e);
        }
        throw new CommonException("异常了");
        //return "test";
    }


    public String testFallbackMethod(Throwable e){
        log.debug("异常了：",e);
        return "testFallbackMethod";
    }

    @GetMapping("/elasticsearch")
    @ApiOperation(value="测试es接口", notes="测试es接口", response = String.class)
    public StandardResult<String> testElasticsearch(){

        boolean test = elasticsearchTemplate.indexExists("test");

        return StandardResult.ok();
    }
}

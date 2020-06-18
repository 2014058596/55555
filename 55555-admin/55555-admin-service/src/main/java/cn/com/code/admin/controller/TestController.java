package cn.com.code.admin.controller;

import cn.com.code.base.bean.CommonException;
import cn.com.code.base.bean.StandardResult;
import cn.com.code.common.utils.RedisUtils;
import cn.com.code.common.utils.SpringContextHelper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
@DependsOn("springContextHelper")
public class TestController {

    private  RedisUtils iPermissionUrlController = SpringContextHelper.getBean(RedisUtils.class);

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

    @PutMapping("/elasticsearch")
    @ApiOperation(value="测试es接口", notes="测试es接口", response = String.class)
    public StandardResult<String> testElasticsearch(String ss){
        String sss = "AAAAAAAAAAAAAAAAA";
        String asd = "BBBBBBBB";
        ClientInterface restClientUtil = ElasticSearchHelper.getRestClientUtil();
        boolean sszt = restClientUtil.existIndice("asdasd");
        return StandardResult.ok();
    }


    public static void main(String[] args) {
        learnStream();
    }


    private static void learnStream() {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
        lists.add(4);
        lists.add(5);
        lists.add(6);

        Optional<Integer> sum = lists.stream().reduce((a, b) -> a + b);
        if (sum.isPresent()) System.out.println("list的总和为:" + sum.get());//21
        //<====> lists.stream().reduce((a, b) -> a + b).ifPresent(System.out::println);

        Integer sum2 = lists.stream().reduce(0, (a, b) -> a + b);//21
        System.out.println("list的总和为:" + sum2);

        Optional<Integer> product = lists.stream().reduce((a, b) -> a * b);
        if (product.isPresent()) System.out.println("list的积为:" + product.get());//720
        Integer product2 = lists.stream().reduce(1, (a, b) -> a * b);
        System.out.println("list的积为:" + product2);//720
        Integer product3 = lists.stream().reduce(1, (a, b) -> {
             return a * b;//这里你可以为所欲为!
        });
        System.out.println("list的偶数的积为:" + product3);//48


    }



    //循环次数
    private static int LOOP_COUNT = 10000000;
    //线程数量
    private static int THREAD_COUNT = 10;
    //元素数量
    private static int ITEM_COUNT = 10;
    private Map<String, Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    //获得一个随机的Key
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    synchronized (freqs) {
                        if (freqs.containsKey(key)) {
                            //Key存在则+1
                            freqs.put(key, freqs.get(key) + 1);
                        } else {
                            //Key不存在则初始化为1
                            freqs.put(key, 1L);
                        }
                    }
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }
}

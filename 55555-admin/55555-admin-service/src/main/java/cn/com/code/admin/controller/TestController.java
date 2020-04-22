package cn.com.code.admin.controller;

import cn.com.code.common.bean.CommonException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月14日 11:07 下午
 */
@RestController
public class TestController {


    @GetMapping("/test")
    public String test(){
        throw new CommonException("test");
    }
}

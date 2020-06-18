package cn.com.code.admin.service.impl;

import cn.com.code.admin.api.pojo.PermissionUrl;
import cn.com.code.common.utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
* PermissionUrlServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>05/28/2020</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class PermissionUrlServiceImplTest {



    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
        /** 
    * 
    * Method: loadResourceDefine() 
    * 
    */ 
    @Test
    public void testLoadResourceDefine() throws Exception {
        PermissionUrl permissionUrl = new PermissionUrl();
        permissionUrl.setId("aaa");
        RedisUtils<Object> use = RedisUtils.use();
        use.set("ss", permissionUrl);
        PermissionUrl ss = (PermissionUrl) use.get("ss");
        log.debug(ss);
    }
    
        
    } 

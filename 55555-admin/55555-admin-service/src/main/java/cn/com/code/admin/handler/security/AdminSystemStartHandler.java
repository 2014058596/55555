package cn.com.code.admin.handler.security;

import cn.com.code.admin.service.IPermissionUrlService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AdminSystemStartHandler
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月23日 12:33 上午
 */
@Log4j2
@Component
@DependsOn({"springContextHelper"})
public class AdminSystemStartHandler implements CommandLineRunner {

    @Autowired
    private IPermissionUrlService permissionUrlService;

    @Override
    public void run(String... args) throws Exception {

        permissionUrlService.loadResourceDefine();
    }

}

package cn.com.test;

import cn.com.code.common.utils.SpringContextHelper;
import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @ClassName: Test
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月17日 5:36 下午
 */
public class Test {


    public static void main(String[] args) throws JSchException {
        String clientUrl = SpringContextHelper.getStringProValue("spring.client.jar.url");
        clientUrl = "/usr/spring-boot/jars/";
        final Session session = JschUtil.createSession("geosocial.ink", 30022, "geostar", "geostar123456");
        session.connect(3000);
        final Channel exec = session.openChannel("exec");
        //JschUtils.upload()


    }


}

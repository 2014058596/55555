package cn.com.test;

import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: JschSession
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月17日 6:39 下午
 */
public enum SSHSession {
    INSTANCE;

    ConcurrentHashMap<String, Session> concurrentHashMap = new ConcurrentHashMap<>();



    SSHSession(){
        concurrentHashMap.put("hanyukun.cn", JschUtil.createSession("hanyukun.cn", 10222,"hanyukun","123456"));
    }

    /**
     * 获取会话
     * @param host
     * @return
     */
    public Session getSessionByHost(String host){
        final Session session = concurrentHashMap.get(host);
        if(session == null){
            throw new RuntimeException(host + "- 对应的Session没有实例化");
        }
        return session;
    }


}

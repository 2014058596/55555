package cn.com.code.common.interceptor.mybatis;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: MybatisRedisPlugin
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月05日 9:55 上午
 */

@Component
@ConditionalOnClass(RedisTemplate.class)
@ConditionalOnProperty(name = "mybatis-plus.executing.saveRedis", havingValue = "true", matchIfMissing = false)
@Log4j2
public class MybatisRedisPlugin {
    MybatisRedisPlugin(){
        System.out.println();
    }

    public static final String MYBATIS_SLOW_SQL = "MYBATIS-SLOW-SQL";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value(value = "${mybatis-plus.executing.slowSqlTime:2 }")
    private Long slowSqlTime;

    /**
     * 存储慢日志
     * @param sql
     * @param totalTime
     * @param sqlId
     */
    public void storingSlowLogs(String sql, long totalTime, String sqlId){
        //小于此时间，不存储
        if(slowSqlTime > totalTime){
            return;
        }
        final ZSetOperations<String, Object> stringObjectZSetOperations = redisTemplate.opsForZSet();
        final ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        final Double score = stringObjectZSetOperations.score(MYBATIS_SLOW_SQL, sql);
        //如果redis时间大则不存储
        if(score != null && score > totalTime){
            return;
        }
        /*stringObjectZSetOperations.add(MYBATIS_SLOW_SQL, sqlId, totalTime);
        stringObjectValueOperations.set(sqlId, sql);*/

        List<String> keyList = new ArrayList();
        Collections.addAll(keyList,MYBATIS_SLOW_SQL, sqlId);
        Object[] values = {sqlId, totalTime , sql};
        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("storingSlowLogs.lua"));
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
        defaultRedisScript.setScriptSource(scriptSource);
        defaultRedisScript.setResultType(Long.class);
        Long result = (Long) redisTemplate.execute(defaultRedisScript, keyList, values);
        log.debug(result);

    }
}

package cn.com.code.common.interceptor.mybatis;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * @ClassName: MybatisPlusExecuteTimeInterceptor
 * @Description: TODO
 * @author: 55555
 * @date: 2020年06月04日 9:38 下午
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Log4j2
@Component
public class MybatisPlusExecuteTimeInterceptor implements Interceptor {

    @Autowired(required = false)
    private MybatisRedisPlugin mybatisRedisPlugin;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("executing");
        try {
            return invocation.proceed();
        } finally {
            try {
                stopWatch.stop();
                final long totalTimeMillis = stopWatch.getTotalTimeMillis();
                StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
                MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
                MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
                if(mappedStatement != null ){
                    // 获取到节点的id,即sql语句的id
                    String sqlId = mappedStatement.getId();
                    // BoundSql就是封装myBatis最终产生的sql类
                    BoundSql boundSql = statementHandler.getBoundSql();
                    // 获取到最终的sql语句
                    String sql = showSql(mappedStatement.getConfiguration(), boundSql);
                    log.info("\n 执行 SQL: {}  \n 执行耗时: {} ms \n 执行 SQL ID:{}", sql, totalTimeMillis, sqlId);
                    if(mybatisRedisPlugin != null){
                        mybatisRedisPlugin.storingSlowLogs(sql, totalTimeMillis, sqlId);
                    }

                }
            }catch (Exception e){
                log.debug(e);
            }
        }
    }



    /**
     * 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj)
    {
        String value = null;
        if (obj instanceof String)
        {
            value = "'" + obj.toString() + "'";
        }
        else if (obj instanceof Date)
        {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        }
        else
        {
            if (obj != null)
            {
                value = obj.toString();
            }
            else
            {
                value = "";
            }

        }
        return value;
    }

    /**
     * 进行？的替换
     * @param configuration
     * @param boundSql
     * @return
     */
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;

    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

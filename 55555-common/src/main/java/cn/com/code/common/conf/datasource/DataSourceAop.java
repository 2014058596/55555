package cn.com.code.common.conf.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/** 
* @ClassName: DataSourceAop
* @Description: 数据源拦截
* @author: 55555
* @date: 2020年04月04日 10:41 下午
*/
@Aspect
@Component
@ConditionalOnClass(DruidDataSource.class)
@Log4j2
public class DataSourceAop {
	
	@Pointcut("@annotation(cn.com.code.common.conf.datasource.DataSource)")
	public void dataSource() {}
	
	@SuppressWarnings("rawtypes")
	@Before("dataSource()")
	public void beforeSwitchDS(JoinPoint point) {
		Class<?> clazz = point.getTarget().getClass();
		String dataSource="default";
		String methodName = point.getSignature().getName();
		Class[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes();
		try {
			//获取执行得方法上得数据源
			Method method = clazz.getMethod(methodName, parameterTypes);
			if(method.isAnnotationPresent(DataSource.class)) {
				dataSource = method.getAnnotation(DataSource.class).value();
			}
			//如果方法上没有数据源从类上获取
			if(StringUtils.isNoneBlank(dataSource)) {
				if(clazz.isAnnotationPresent(DataSource.class)) {
					dataSource = clazz.getAnnotation(DataSource.class).value();
				}
			}
			
		} catch (Exception e) {
			log.debug(e);
		} 
		
		System.out.println(dataSource);
		DataSourceHandler.put(dataSource);
	}
	
	@After("dataSource()")
	public void clear(){
		DataSourceHandler.clear();
	}
	
	
	
	
}

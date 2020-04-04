package cn.com.code.common.conf.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @ClassName: DataSource
* @Description: 制定数据源
* @author: 55555
* @date: 2020年04月04日 11:09 下午
*/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
	
	String value() default "default";
	
}

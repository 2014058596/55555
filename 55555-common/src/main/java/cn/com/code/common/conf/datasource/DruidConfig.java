package cn.com.code.common.conf.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Order(1)
//@Configuration
public class DruidConfig {

	@Autowired
	private DataSource dataSource;
	
	//动态数据源
	@Bean("default")
	@Primary//默认选此数据源
	public DataSource dynamicDataSource() {
		//自己实现得类，继承AbstractRoutingDataSource他实现了datesource接口
		DynamicDataSource dds=new DynamicDataSource();
		dds.setDefaultTargetDataSource(dataSource);
		Map<Object,Object> map=new HashMap<>(16);
		map.put("fbpt",dataSource);
		dds.setTargetDataSources(map);
		return dds;
	}
		
	


    
}

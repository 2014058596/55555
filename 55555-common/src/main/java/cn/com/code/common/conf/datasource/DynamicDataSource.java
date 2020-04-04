package cn.com.code.common.conf.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/** 
* @ClassName: DynamicDataSource
* @Description: 动态数据源
* @author: 55555
* @date: 2020年04月04日 10:45 下午
*/
public class DynamicDataSource extends AbstractRoutingDataSource{
	
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHandler.get();
	}
	
	
}

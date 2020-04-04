package cn.com.code.common.conf.datasource;

/** 
* @ClassName: DataSourceHandler
* @Description: 数据源处理类
* @author: 55555
* @date: 2020年04月04日 10:42 下午
*/
public class DataSourceHandler {
	
	private static ThreadLocal<String> handlerThredLocal = new ThreadLocal<>();
	
	
	public static void put(String dataSource) {
		handlerThredLocal.set(dataSource);
	}
	
	public static void clear() {
		handlerThredLocal.remove();
	}
	
	public static String get() {
		return handlerThredLocal.get();
	}
	
	
	
}

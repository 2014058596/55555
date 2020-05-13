package cn.com.code.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/** 
* @ClassName: JsonUtils
* @Description: TODO
* @author: 55555
* @date: 2020年04月04日 10:30 下午
*/
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			return MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json解析错误");
		}
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
        	throw new RuntimeException("Json解析错误");
        }
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		return MAPPER.readValue(jsonData, javaType);
		} catch (Exception e) {
			throw new RuntimeException("Json解析错误");
		}
    	
    }
    
    /**
     * 对象转换成目标对象
     * @param data
     * @param beanType
     * @return
     */
    public static <T> T objectToObject(Object data, Class<T> beanType) {
    	return jsonToPojo(objectToJson(data), beanType);
    }
    
    
    /**
     *  判断是否是一个标准的json字符串
     * @param json
     * @return
     */
    public static boolean isJSON(String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

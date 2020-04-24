package cn.com.code.common.utils;

/** 
* @ClassName: CommonUtils
* @Description: TODO
* @author: 55555
* @date: 2020年04月23日 10:31 下午
*/
@SuppressWarnings("ALL")
public class CommonUtils {




    /**
     * 获取雪花算法id
     * @return
     */
    public static long getId(){
        IdWorker idWorker = new IdWorker(0,0);
        return idWorker.nextId();
    }

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }


}

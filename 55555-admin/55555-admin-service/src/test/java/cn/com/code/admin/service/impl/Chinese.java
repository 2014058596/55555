package cn.com.code.admin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: Chanese
 * @Description: TODO
 * @author: 55555
 * @date: 2020年05月30日 11:50 下午
 */
public class Chinese extends Person {


    public void test(String ss){
        System.out.println("chinese");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method test = Chinese.class.getMethod("test", Object.class);
        test.invoke(new Chinese(),"sss");
    }
}

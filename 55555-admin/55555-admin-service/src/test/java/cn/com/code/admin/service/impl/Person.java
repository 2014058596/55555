package cn.com.code.admin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @ClassName: Person
 * @Description: TODO
 * @author: 55555
 * @date: 2020年05月30日 11:49 下午
 */

public class Person<T> {

    public void test(T t){
        System.out.println("person");
    }


    interface IPerson{

        String say();
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        IPerson chinese = (IPerson) Proxy.newProxyInstance(IPerson.class.getClassLoader(), new Class[]{IPerson.class}, (proxy, method, args1) -> {
            if ("say".equals(method.getName())) {
                System.out.println("chinese");
            }
            return null;
        });
        chinese.say();
    }
}

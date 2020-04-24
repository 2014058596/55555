package cn.com.code.common.bean;

import cn.com.code.admin.api.model.UserModel;

/**
 * @ClassName: ThreadLocalBean
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月22日 11:51 下午
 */
public class ThreadLocalBean {

    public static ThreadLocal<UserModel> LOGIN_USER = new ThreadLocal<>();

}

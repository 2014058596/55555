package cn.com.code.admin.service;

import cn.com.code.admin.api.model.UserModel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 */
public interface IUserService extends IService<UserModel> {
    /**
     * 根据userName获取用户信息
     * @param userName
     * @return
     */
    UserModel selectUserModelByUserName(String  userName);
	
}

package cn.com.code.admin.service.impl;

import cn.com.code.admin.api.model.UserModel;
import cn.com.code.admin.mapper.UserMapper;
import cn.com.code.admin.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 根据userName获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public UserModel selectUserModelByUserName(String userName) {
        Wrapper<UserModel> wrapper = new EntityWrapper<>();
        wrapper.eq(UserModel.USER_NAME, userName);
        return selectOne(wrapper);
    }
}

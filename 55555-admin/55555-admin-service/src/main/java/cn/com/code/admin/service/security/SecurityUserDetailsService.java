package cn.com.code.admin.service.security;

import cn.com.code.admin.api.model.UserModel;
import cn.com.code.admin.mapper.UserMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @ClassName: SecurityUserDetailsService
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月21日 10:58 上午
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public SecurityUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Wrapper<UserModel> wrapper = new EntityWrapper<>();
        wrapper.eq(UserModel.USER_NAME, userName);
        List<UserModel> userModels = userMapper.selectUserModel(wrapper);

        if(userModels != null && !userModels.isEmpty()){
            return new SecurityUserDetails(userModels.get(0));
        }
        throw new UsernameNotFoundException("没找到此用户");

    }
}

package cn.com.code.admin.service.security;

import cn.com.code.admin.api.model.UserModel;
import cn.com.code.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SecurityUserDetailsService
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月21日 10:58 上午
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel userModel = userService.selectUserModelByUserName(s);
        if(userModel == null){
            throw new UsernameNotFoundException("没找到此用户");
        }
        return new SecurityUserDetails(userModel);
    }
}

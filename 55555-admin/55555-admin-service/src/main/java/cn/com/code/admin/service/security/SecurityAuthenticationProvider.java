package cn.com.code.admin.service.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AuthenticationProvider
 * @Description: 自定义用户密码验证
 * @author: 55555
 * @date: 2020年04月21日 2:15 下午
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecurityUserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String presentedPassword = (String)authentication.getCredentials();
        // 根据用户名获取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // TODO 自定义的加密规则，用户名、输的密码和数据库保存的盐值进行加密

        if (StringUtils.isBlank(presentedPassword)) {
            throw new BadCredentialsException("登录名或密码错误");
        } else if (!presentedPassword.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("登录名或密码错误");
        } else {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

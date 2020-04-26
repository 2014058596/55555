package cn.com.code.admin.conf.security;

import cn.com.code.admin.handler.security.SecurityAuthenticationFailureHandler;
import cn.com.code.admin.handler.security.SecurityAuthenticationSuccessHandler;
import cn.com.code.admin.handler.security.SecurityLogoutSuccessHandler;
import cn.com.code.admin.service.security.SecurityAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName: SecurityConf
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月20日 11:00 下午
 */
@Configuration
public class SecurityConf  extends WebSecurityConfigurerAdapter {



    @Autowired
    private SecurityAuthenticationFailureHandler failureHandler;

    @Autowired
    private SecurityAuthenticationSuccessHandler successHandler;

    @Autowired
    private SecurityLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SecurityAuthenticationProvider authenticationProvider;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启登录配置
        http.authorizeRequests()
                .and()
                .formLogin()
                //登录处理接口
                .loginProcessingUrl("/doLogin")
                //定义登录时，用户名的 key，默认为 username
                .usernameParameter("userId")
                //定义登录时，用户密码的 key，默认为 password
                .passwordParameter("password")
                //登录成功的处理器
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                //和表单登录相关的接口统统都直接通过
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        //自定义登录处理
        http.authenticationProvider(authenticationProvider);
        //AuthorizationCodeServices
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/test");
    }




}

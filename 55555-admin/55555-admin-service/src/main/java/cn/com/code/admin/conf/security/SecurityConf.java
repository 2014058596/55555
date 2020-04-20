package cn.com.code.admin.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * @ClassName: SecurityConf
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月20日 11:00 下午
 */
@Configuration
public class SecurityConf  extends WebSecurityConfigurerAdapter {


    @Autowired
    private AccessTokenFilter accessTokenFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //开启登录配置
        http.authorizeRequests()
                //表示访问 /hello 这个接口，需要具备 admin 这个角色
                .antMatchers("/hello").hasRole("admin")
                //表示剩余的其他接口，登录之后就能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //登录处理接口
                .loginProcessingUrl("/doLogin")
                //定义登录时，用户名的 key，默认为 username
                .usernameParameter("userId")
                //定义登录时，用户密码的 key，默认为 password
                .passwordParameter("password")
                //登录成功的处理器
                .successHandler((req, resp, authentication) -> {
                    PrintWriter out = resp.getWriter();
                    out.write("success");
                    out.flush();
                })
                .failureHandler((req, resp, exception) -> {
                    PrintWriter out = resp.getWriter();
                    out.write("fail");
                    out.flush();
                })
                //和表单登录相关的接口统统都直接通过
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("logout success");
                    out.flush();
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

}

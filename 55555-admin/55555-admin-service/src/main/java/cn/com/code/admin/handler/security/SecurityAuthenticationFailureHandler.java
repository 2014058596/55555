package cn.com.code.admin.handler.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SecurityAuthenticationFailureHandler
 * @Description: 登录失败处理器
 * @author: 55555
 * @date: 2020年04月21日 12:03 上午
 */
@Component
@Log4j2
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler
{
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.debug(e);
    }
}

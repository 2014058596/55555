package cn.com.code.admin.conf.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName: AccessTokenFilter
 * @Description:
 * @author: 55555
 * @date: 2020年04月20日 11:11 下午
 */
@Component
public class AccessTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json;charset=utf-8");

        filterChain.doFilter(servletRequest,servletResponse);
    }
}

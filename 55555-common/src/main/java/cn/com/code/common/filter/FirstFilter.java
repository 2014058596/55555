package cn.com.code.common.filter;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

import static cn.com.code.common.bean.ThreadLocalBean.*;

/**
 * @ClassName: FirstFilter
 * @Description: 清空本地缓存变量
 * @author: 55555
 * @date: 2020年04月23日 12:16 上午
 */
@Component
public class FirstFilter implements Filter, Ordered {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGIN_USER.remove();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

package cn.com.code.admin.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TokenAuthenticationFilter
 * @Description: Token鉴权
 * @author: 55555
 * @date: 2020年04月21日 9:31 上午
 */
public class TokenAuthenticationFilter extends GenericFilterBean {


    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest)request;

        String accessToken = httpRequest.getHeader("accessToken");
        if (null != accessToken) {
            //获取并检查令牌是否有效(从存储令牌的DB或文件中)
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("admin");
            List<SimpleGrantedAuthority> list = new ArrayList();
            list.add(simpleGrantedAuthority);
            //通过使用令牌获取相关信息来填充securityContext older
            final User user = new User(
                    "root",
                    "root",
                    true,
                    true,
                    true,
                    true,
                    list
                    );
            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        }else {
            chain.doFilter(request, response);
        }

    }

}

package cn.com.code.common.filter;

import cn.com.code.admin.api.model.UserModel;
import cn.com.code.common.bean.StandardResult;
import cn.com.code.common.service.security.SecurityUserDetails;
import cn.com.code.common.utils.JsonUtils;
import cn.com.code.common.utils.RedisUtils;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static cn.com.code.common.bean.HttpStatus.EXPIRED_TOKEN;


/**
 * @ClassName: TokenAuthenticationFilter
 * @Description: Token鉴权
 * @author: 55555
 * @date: 2020年04月21日 9:31 上午
 */
@Component
public class TokenAuthenticationFilter extends GenericFilterBean implements Ordered {



    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest)request;
        response.setContentType("application/json;charset=utf-8");

        String accessToken = httpRequest.getHeader("accessToken");
        if (null != accessToken) {
            //获取并检查令牌是否有效(从存储令牌的DB或文件中)
            UserModel userModel = (UserModel) RedisUtils.use().get(accessToken);
            UserDetails userDetails = new SecurityUserDetails(userModel);
            final UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        }else {
            response.getWriter().print(JsonUtils.objectToJson(StandardResult.faild(EXPIRED_TOKEN)));
        }

    }

    @Override
    public int getOrder() {
        return 2;
    }
}

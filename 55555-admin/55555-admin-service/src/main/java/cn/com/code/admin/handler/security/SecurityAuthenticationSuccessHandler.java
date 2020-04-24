package cn.com.code.admin.handler.security;

import cn.com.code.admin.api.model.UserModel;
import cn.com.code.common.bean.StandardResult;
import cn.com.code.common.utils.CommonUtils;
import cn.com.code.common.utils.JsonUtils;
import cn.com.code.common.utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static cn.com.code.common.bean.ThreadLocalBean.LOGIN_USER;

/**
 * @ClassName: SecurityAuthenticationSuccessHandler
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月21日 12:05 上午
 */
@Component
@Log4j2
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Map<String, Object> resultMap = new HashMap<>(3);
        log.debug("------登录成功-----");
        UserModel userModel = LOGIN_USER.get();
        String token = CommonUtils.getUUID();
        RedisUtils.use().set(token, userModel, 1800);
        resultMap.put("accessToken", token);
        resultMap.put("user", userModel);
        httpServletResponse.getWriter().print(JsonUtils.objectToJson(StandardResult.ok(resultMap)));
    }
}

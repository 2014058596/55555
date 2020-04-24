package cn.com.code.common.service.security;

import cn.com.code.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.com.code.common.bean.Constants.PERMISSION_KEY;

/**
 * @ClassName: AccessSecurityMetadataSource
 * 权限资源映射的数据源 <br>
 * 这里重写并实现了基于数据库的权限数据源 <br>
 * 实现了 {@link FilterInvocationSecurityMetadataSource}接口 <br>
 * 框架的默认实现是 {@link DefaultFilterInvocationSecurityMetadataSource} <br>* @author: 55555
 * @date: 2020年04月21日 5:16 下午
*/
@Slf4j
@Service
public class AccessSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {








    /**
     * 鉴权时会被AbstractSecurityInterceptor.beforeInvocation()调用，根据URL找到对应需要的权限
     *
     * @param object 安全对象类型 FilterInvocation.class
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Map<String, Collection<String>> permissionMap = (Map<String, Collection<String>>) RedisUtils.use().get(PERMISSION_KEY);
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<String, Collection<String>> entry : permissionMap.entrySet()) {
            String[] split = entry.getKey().split(",");
            RequestMatcher requestMatcher = new AntPathRequestMatcher(split[0], split[1]);
            if (requestMatcher.matches(request)) {
                log.info("[找到的Key]: {}", entry.getKey());
                log.info("[找到的Value]: {}", entry.getValue());
                if (entry.getValue().size() > 0) {
                    return getConfigAttributeList(entry.getValue());
                }
            }
        }
        return null;
    }

    /**
     * 用于被AbstractSecurityInterceptor调用，返回所有的 Collection<ConfigAttribute> ，以筛选出不符合要求的attribute
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    /**
     * 用于被AbstractSecurityInterceptor调用，验证指定的安全对象类型是否被MetadataSource支持
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 获取List
     * @return
     */
    public Collection<ConfigAttribute> getConfigAttributeList(Collection<String> list){
        return list.stream().map(role -> new SecurityConfig(role)).collect(Collectors.toList());
    }
}

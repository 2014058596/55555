package cn.com.code.admin.service.security;

import cn.com.code.admin.api.model.PermissionUrlModel;
import cn.com.code.admin.service.IPermissionUrlService;
import cn.com.code.common.bean.Constants;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    // key 是url+method ， value 是对应url资源的角色列表
    private static Map<RequestMatcher, Collection<ConfigAttribute>> permissionMap;

    @Autowired
    private IPermissionUrlService permissionUrlService;


    /**
     * 在Web服务器启动时，缓存系统中的所有权限映射。<br>
     * 被{@link PostConstruct}修饰的方法会在服务器加载Servlet的时候运行(构造器之后,init()之前) <br/>
     */
    @PostConstruct
    private void loadResourceDefine() {
        permissionMap = new LinkedHashMap<>();
        // 需要鉴权的url资源，@needAuth标志
        Wrapper<PermissionUrlModel> wrapper = new EntityWrapper<>();
        wrapper.eq(PermissionUrlModel.IS_DELETE, Constants.IS_DELETE_NO.getValue());
        List<PermissionUrlModel> permissionList = permissionUrlService.selectList(wrapper);
        for (PermissionUrlModel permission : permissionList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            String[] roles = permission.getRole().split(",");
            log.info("{} - {}", url, method);
            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, method);

            Collection<ConfigAttribute> attributes = new ArrayList<>();
            for (String role : roles) {
                attributes.add(new SecurityConfig(role));
            }
            // 占位符，需要权限才能访问的资源 都需要添加一个占位符，保证value不是空的
            attributes.add(new SecurityConfig("@needAuth"));
            permissionMap.put(requestMatcher, attributes);
        }

        Collection<ConfigAttribute> attributes = new ArrayList<>();
        // 多余的url资源， @noAuth，所有人不鉴权
        permissionMap.put(new AntPathRequestMatcher("/**", null), attributes);
        log.info("[全局权限映射集合初始化]: {}", permissionMap.toString());
    }

    /**
     * 鉴权时会被AbstractSecurityInterceptor.beforeInvocation()调用，根据URL找到对应需要的权限
     *
     * @param object 安全对象类型 FilterInvocation.class
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("[资源被访问：根据URL找到权限配置]: {}\n {}", object, permissionMap);

        if (permissionMap == null) {
            loadResourceDefine();
        }
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                log.info("[找到的Key]: {}", entry.getKey());
                log.info("[找到的Value]: {}", entry.getValue());
                if (entry.getValue().size() > 0) {
                    return entry.getValue();
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
}

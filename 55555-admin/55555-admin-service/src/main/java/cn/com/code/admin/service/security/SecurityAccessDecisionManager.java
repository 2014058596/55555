package cn.com.code.admin.service.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * @ClassName: SecurityAccessDecisionManager
 * @Description:  * 被鉴权决策管理器 被{ AbstractSecurityInterceptor} 调用进行鉴权 <br>
 * @author: 55555
 * @date: 2020年04月21日 5:10 下午
 */
@Log4j2
@Service
public class SecurityAccessDecisionManager implements AccessDecisionManager {
        /**
         * 权限鉴定
         *
         * @param authentication   from SecurityContextHolder.getContext() =》 userDetails.getAuthorities()
         * @param object           是一个安全对象类型，FilterInvocation.class
         * @param configAttributes from MetaDataSource.getAttributes()，已经被框架做了非空判断
         */
        @Override
        public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
                throws AccessDeniedException, InsufficientAuthenticationException {

            log.info("[资源权限]: {}", configAttributes);
            log.info("[用户权限]: {}", authentication.getAuthorities());

            Iterator<ConfigAttribute> it = configAttributes.iterator();
            while (it.hasNext()) {
                // 资源的权限
                ConfigAttribute resourceAttr = it.next();
                String resourceRole = "ROLE_" + resourceAttr.getAttribute();

                // 用户的权限
                for (GrantedAuthority userAuth : authentication.getAuthorities()) {
                    log.info("[资源角色==用户角色] ？ {} == {}", resourceRole.trim(), userAuth.getAuthority().trim());
                    if (resourceRole.trim().equals(userAuth.getAuthority().trim())) {
                        return;
                    }
                }
            }
            throw new AccessDeniedException("权限不足");
        }

        /**
         * 被AbstractSecurityInterceptor调用，遍历ConfigAttribute集合，筛选出不支持的attribute
         */
        @Override
        public boolean supports(ConfigAttribute attribute) {
            return true;
        }

        /**
         * 被AbstractSecurityInterceptor调用，验证AccessDecisionManager是否支持这个安全对象的类型。
         */
        @Override
        public boolean supports(Class<?> clazz) {
            return true;
        }
}

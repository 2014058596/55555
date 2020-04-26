package cn.com.code.admin.service.impl;

import cn.com.code.admin.api.model.PermissionUrlModel;
import cn.com.code.admin.mapper.PermissionUrlMapper;
import cn.com.code.admin.service.IPermissionUrlService;
import cn.com.code.base.bean.Constants;
import cn.com.code.common.utils.RedisUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

import static cn.com.code.base.bean.Constants.PERMISSION_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 */
@Service
@Log4j2
public class PermissionUrlServiceImpl extends ServiceImpl<PermissionUrlMapper, PermissionUrlModel> implements IPermissionUrlService {




    /**
     * 加载数据到redis
     */
    @Override
    public void loadResourceDefine() {
        //key 值为 url路径 + , + method  值为角色集合
        Map<String, Collection<String>> permissionMap = new LinkedHashMap<>();
        // 需要鉴权的url资源，@needAuth标志
        Wrapper<PermissionUrlModel> wrapper = new EntityWrapper<>();
        wrapper.eq(PermissionUrlModel.IS_DELETE, Constants.IS_DELETE_NO);
        List<PermissionUrlModel> permissionList = selectList(wrapper);
        for (PermissionUrlModel permission : permissionList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            String[] roles = permission.getRole().split(",");
            log.info("{} - {}", url, method);
            Collection<String> attributes = new ArrayList<>();
            for (String role : roles) {
                attributes.add(role);
            }
            // 占位符，需要权限才能访问的资源 都需要添加一个占位符，保证value不是空的
            attributes.add("@needAuth");
            permissionMap.put(url + "," + method, attributes);
        }
        List<String> list = new ArrayList<>(1);
        // 多余的url资源， @noAuth，所有人不鉴权
        // /**,是因为后面省略了method  后面会对URL进行鉴权匹配
        permissionMap.put("/**,", list);
        log.info("[全局权限映射集合初始化]: {}", permissionMap.toString());
        new RedisUtils<Map<String, Collection<String>>>().set(PERMISSION_KEY, permissionMap);
    }
}

package cn.com.code.admin.service;

import cn.com.code.admin.api.model.PermissionUrlModel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 */
public interface IPermissionUrlService extends IService<PermissionUrlModel> {

    /**
     * 加载权限到redis
     */
    void loadResourceDefine();

}

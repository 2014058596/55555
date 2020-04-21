package cn.com.code.admin.api.controller;

import cn.com.code.admin.api.constants.AdminConstants;
import cn.com.code.admin.api.model.PermissionUrlModel;
import cn.com.code.common.bean.StandardResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;



/**
 *
 *   PermissionUrl API接口    
 *
 *   @author 55555
 *   @since 2020-04-21
 */

@FeignClient(name = AdminConstants.APPLICATION_NAME)
public interface IPermissionUrlController {

    
    /**
     * 获取分页列表
     *
     * @param pageSize
     * @param pageNumber
     * @param permissionUrlModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/permissionUrl")
    public StandardResult selectPage(
            @SpringQueryMap PermissionUrlModel permissionUrlModel,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber);

    /**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */

    @GetMapping("/permissionUrlList")
    public StandardResult selectList(@SpringQueryMap PermissionUrlModel permissionUrlModel);





    /**
     * 通过id获取PermissionUrlModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/permissionUrl/{id}")
    public StandardResult selectById(@PathVariable("id") String id);

    /**
     * 通过id删除PermissionUrlModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @DeleteMapping("/permissionUrl/{id}")
    public StandardResult deleteById(@PathVariable("id") String id);

    /**
     * 添加PermissionUrlModel
     *
     * @param permissionUrlModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PostMapping("/permissionUrl")
    public StandardResult insert(@ModelAttribute PermissionUrlModel permissionUrlModel);

    /**
     * 更新PermissionUrlModel
     *
     * @param permissionUrlModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PutMapping("/permissionUrl")
    public StandardResult updateById(@RequestBody PermissionUrlModel permissionUrlModel);
    
    

}

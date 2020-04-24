package cn.com.code.admin.api.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import cn.com.code.admin.api.model.PermissionUrlModel;
import cn.com.code.admin.api.constants.AdminConstants;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;



/**
 *
 *   PermissionUrl API接口    
 *
 *   @author 55555
 *   @since 2020-04-24
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
     * @since : Create in 2020-04-24
     */
    @GetMapping("/permissionUrl")
    Page<PermissionUrlModel> selectPage(
            @SpringQueryMap PermissionUrlModel permissionUrlModel,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber);

    /**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-24
     */

    @GetMapping("/permissionUrlList")
    List<PermissionUrlModel> selectList(@SpringQueryMap PermissionUrlModel permissionUrlModel);



    /**
     * 通过id获取PermissionUrlModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @GetMapping("/permissionUrl/{id}")
    PermissionUrlModel selectById(@PathVariable("id") String id);

    /**
     * 通过id删除PermissionUrlModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @DeleteMapping("/permissionUrl/{id}")
    Boolean deleteById(@PathVariable("id") String id);

    /**
     * 添加PermissionUrlModel
     *
     * @param permissionUrlModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @PostMapping("/permissionUrl")
    Boolean insert(@ModelAttribute PermissionUrlModel permissionUrlModel);

    /**
     * 更新PermissionUrlModel
     *
     * @param permissionUrlModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @PutMapping("/permissionUrl")
    Boolean updateById(@RequestBody PermissionUrlModel permissionUrlModel);
    
    

}

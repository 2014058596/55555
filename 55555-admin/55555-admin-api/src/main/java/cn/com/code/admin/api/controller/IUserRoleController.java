package cn.com.code.admin.api.controller;

import cn.com.code.common.bean.StandardResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import cn.com.code.admin.api.model.UserRoleModel;
import java.util.Map;
import cn.com.code.admin.api.constants.AdminConstants;



/**
 *
 *   UserRole API接口    
 *
 *   @author 55555
 *   @since 2020-04-21
 */

@FeignClient(name = AdminConstants.APPLICATION_NAME)
public interface IUserRoleController {

    
    /**
     * 获取分页列表
     *
     * @param pageSize
     * @param pageNumber
     * @param userRoleModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/userRole")
    public StandardResult selectPage(
            @SpringQueryMap UserRoleModel userRoleModel,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber);

    /**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */

    @GetMapping("/userRoleList")
    public StandardResult selectList(@SpringQueryMap UserRoleModel userRoleModel);





    /**
     * 通过id获取UserRoleModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/userRole/{id}")
    public StandardResult selectById(@PathVariable("id") String id);

    /**
     * 通过id删除UserRoleModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @DeleteMapping("/userRole/{id}")
    public StandardResult deleteById(@PathVariable("id") String id);

    /**
     * 添加UserRoleModel
     *
     * @param userRoleModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PostMapping("/userRole")
    public StandardResult insert(@ModelAttribute UserRoleModel userRoleModel);

    /**
     * 更新UserRoleModel
     *
     * @param userRoleModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PutMapping("/userRole")
    public StandardResult updateById(@RequestBody UserRoleModel userRoleModel);
    
    

}

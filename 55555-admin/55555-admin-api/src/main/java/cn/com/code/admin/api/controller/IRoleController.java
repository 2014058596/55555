package cn.com.code.admin.api.controller;

import cn.com.code.common.bean.StandardResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import cn.com.code.admin.api.model.RoleModel;
import java.util.Map;
import cn.com.code.admin.api.constants.AdminConstants;



/**
 *
 *   Role API接口    
 *
 *   @author 55555
 *   @since 2020-04-21
 */

@FeignClient(name = AdminConstants.APPLICATION_NAME)
public interface IRoleController {

    
    /**
     * 获取分页列表
     *
     * @param pageSize
     * @param pageNumber
     * @param roleModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/role")
    public StandardResult selectPage(
            @SpringQueryMap RoleModel roleModel,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber);

    /**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */

    @GetMapping("/roleList")
    public StandardResult selectList(@SpringQueryMap RoleModel roleModel);





    /**
     * 通过id获取RoleModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/role/{id}")
    public StandardResult selectById(@PathVariable("id") String id);

    /**
     * 通过id删除RoleModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @DeleteMapping("/role/{id}")
    public StandardResult deleteById(@PathVariable("id") String id);

    /**
     * 添加RoleModel
     *
     * @param roleModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PostMapping("/role")
    public StandardResult insert(@ModelAttribute RoleModel roleModel);

    /**
     * 更新RoleModel
     *
     * @param roleModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PutMapping("/role")
    public StandardResult updateById(@RequestBody RoleModel roleModel);
    
    

}

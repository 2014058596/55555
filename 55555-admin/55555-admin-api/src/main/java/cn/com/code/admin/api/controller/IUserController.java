package cn.com.code.admin.api.controller;

import cn.com.code.admin.api.constants.AdminConstants;
import cn.com.code.admin.api.model.UserModel;
import cn.com.code.common.bean.StandardResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;



/**
 *
 *   User API接口    用户表
 *
 *   @author 55555
 *   @since 2020-04-21
 */

@FeignClient(name = AdminConstants.APPLICATION_NAME)
public interface IUserController {

    
    /**
     * 获取分页列表
     *
     * @param pageSize
     * @param pageNumber
     * @param userModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/user")
    public StandardResult selectPage(
            @SpringQueryMap UserModel userModel,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber);

    /**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */

    @GetMapping("/userList")
    public StandardResult selectList(@SpringQueryMap UserModel userModel);





    /**
     * 通过id获取UserModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @GetMapping("/user/{id}")
    public StandardResult selectById(@PathVariable("id") String id);

    /**
     * 通过id删除UserModel
     *
     * @param id
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @DeleteMapping("/user/{id}")
    public StandardResult deleteById(@PathVariable("id") String id);

    /**
     * 添加UserModel
     *
     * @param userModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PostMapping("/user")
    public StandardResult insert(@ModelAttribute UserModel userModel);

    /**
     * 更新UserModel
     *
     * @param userModel
     * @return
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @PutMapping("/user")
    public StandardResult updateById(@RequestBody UserModel userModel);
    
    

}

package cn.com.code.admin.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.com.code.common.bean.StandardResult;
import cn.com.code.common.bean.PaginationResult;

import cn.com.code.admin.service.IUserService;
import cn.com.code.admin.api.model.UserModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.com.code.admin.api.controller.IUserController;
/**
 *　　
 *   User 控制器    用户表
 *
 *   @author 55555
 *   @since 2020-04-21
 */

@RestController
@Api(tags="UserController 用户表控制器")
public class UserController implements IUserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public IUserService userService;

    /**
     * 获取分页列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value="获取分页列表  55555", notes="获取分页列表  55555", response = UserModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页大小", required = true, dataType = "int", defaultValue = "10"),
        @ApiImplicitParam(paramType="query", name = "pageNumber", value = "页数", required = true, dataType = "int", defaultValue = "1")
    })
    @GetMapping("/user")
    public StandardResult selectPage(@ModelAttribute UserModel userModel, Integer pageSize, Integer pageNumber) {
		try {
			if (pageSize == null || pageNumber == null) {
				return StandardResult.faild("缺少必要的分页参数！");
			}
	     	Page<UserModel> page = new Page<UserModel>(pageNumber, pageSize);
	     	Wrapper<UserModel> wrapper = new EntityWrapper<UserModel>(userModel);
	     	userService.selectPage(page, wrapper);
			return PaginationResult.ok(null, page.getRecords(), page.getTotal(), page.getPages());
		} catch (Exception e) {
			logger.error("异常信息:", e);
			return PaginationResult.faild(e);
		}
    }

	/**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value="获取列表  55555", notes="获取列表  55555", response = UserModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("/userList")
    public StandardResult selectList(@ModelAttribute UserModel userModel) {
		try {
			Wrapper<UserModel> wrapper = new EntityWrapper<UserModel>(userModel);
			return StandardResult.ok(null, userService.selectList(wrapper));
		} catch (Exception e) {
			logger.error("异常信息:", e);
			return StandardResult.faild(e);
		}
    }

     /**
     * 添加
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value = "添加  55555", notes = "添加User 55555", response = UserModel.class)
    @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    @PostMapping("/user")
    public StandardResult insert(@ModelAttribute UserModel userModel) {
		try {
            userService.insert(userModel);
            return StandardResult.ok();
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return StandardResult.faild(e);
        }
    }

    /**
     * 修改
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value="修改  55555", notes="更新User 55555", response = UserModel.class)
    @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    @PutMapping("/user")
    public StandardResult updateById(@RequestBody UserModel userModel) {
        try {
            userService.updateById(userModel);
            return StandardResult.ok();
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return StandardResult.faild(e);
        }
    }

    /**
     * 通过id获取详情
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value="通过id获取详情  55555", notes="通过id获取详情  55555", response = UserModel.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
    	@ApiImplicitParam(paramType="path", name = "id", value = "主键id", dataType = "String", required = true)
    })
    @GetMapping("/user/{id}")
    public StandardResult selectById(@PathVariable String id) {
		try {
		     return StandardResult.ok(userService.selectById(id));
		} catch (Exception e) {
		     logger.error("异常信息:", e);
		     return StandardResult.faild(e);
		}
    }

    /**
     * 通过id删除数据
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value="通过id删除数据  55555", notes="通过id删除数据  55555", response = UserModel.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
     	@ApiImplicitParam(paramType="path", name = "id", value = "主键id", dataType = "String", required = true)
    })
    @DeleteMapping("/user/{id}")
    public StandardResult deleteById(@PathVariable String id) {
        try {
            userService.deleteById(id);
            return StandardResult.ok();
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return StandardResult.faild(e);
        }
    }
}

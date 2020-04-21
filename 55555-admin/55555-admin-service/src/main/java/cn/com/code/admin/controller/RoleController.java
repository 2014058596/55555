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

import cn.com.code.admin.service.IRoleService;
import cn.com.code.admin.api.model.RoleModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.com.code.admin.api.controller.IRoleController;
/**
 *　　
 *   Role 控制器    
 *
 *   @author 55555
 *   @since 2020-04-21
 */

@RestController
@Api(tags="RoleController 控制器")
public class RoleController implements IRoleController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public IRoleService roleService;

    /**
     * 获取分页列表
     *
     * @author : 55555
     * @since : Create in 2020-04-21
     */
    @Override
    @ApiOperation(value="获取分页列表  55555", notes="获取分页列表  55555", response = RoleModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页大小", required = true, dataType = "int", defaultValue = "10"),
        @ApiImplicitParam(paramType="query", name = "pageNumber", value = "页数", required = true, dataType = "int", defaultValue = "1")
    })
    @GetMapping("/role")
    public StandardResult selectPage(@ModelAttribute RoleModel roleModel, Integer pageSize, Integer pageNumber) {
		try {
			if (pageSize == null || pageNumber == null) {
				return StandardResult.faild("缺少必要的分页参数！");
			}
	     	Page<RoleModel> page = new Page<RoleModel>(pageNumber, pageSize);
	     	Wrapper<RoleModel> wrapper = new EntityWrapper<RoleModel>(roleModel);
	     	roleService.selectPage(page, wrapper);
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
    @ApiOperation(value="获取列表  55555", notes="获取列表  55555", response = RoleModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("/roleList")
    public StandardResult selectList(@ModelAttribute RoleModel roleModel) {
		try {
			Wrapper<RoleModel> wrapper = new EntityWrapper<RoleModel>(roleModel);
			return StandardResult.ok(null, roleService.selectList(wrapper));
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
    @ApiOperation(value = "添加  55555", notes = "添加Role 55555", response = RoleModel.class)
    @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    @PostMapping("/role")
    public StandardResult insert(@ModelAttribute RoleModel roleModel) {
		try {
            roleService.insert(roleModel);
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
    @ApiOperation(value="修改  55555", notes="更新Role 55555", response = RoleModel.class)
    @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    @PutMapping("/role")
    public StandardResult updateById(@RequestBody RoleModel roleModel) {
        try {
            roleService.updateById(roleModel);
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
    @ApiOperation(value="通过id获取详情  55555", notes="通过id获取详情  55555", response = RoleModel.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
    	@ApiImplicitParam(paramType="path", name = "id", value = "主键id", dataType = "String", required = true)
    })
    @GetMapping("/role/{id}")
    public StandardResult selectById(@PathVariable String id) {
		try {
		     return StandardResult.ok(roleService.selectById(id));
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
    @ApiOperation(value="通过id删除数据  55555", notes="通过id删除数据  55555", response = RoleModel.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
     	@ApiImplicitParam(paramType="path", name = "id", value = "主键id", dataType = "String", required = true)
    })
    @DeleteMapping("/role/{id}")
    public StandardResult deleteById(@PathVariable String id) {
        try {
            roleService.deleteById(id);
            return StandardResult.ok();
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return StandardResult.faild(e);
        }
    }
}

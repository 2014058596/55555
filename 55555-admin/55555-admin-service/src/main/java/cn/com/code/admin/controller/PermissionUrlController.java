package cn.com.code.admin.controller;

import cn.com.code.admin.api.controller.IPermissionUrlController;
import cn.com.code.admin.api.model.PermissionUrlModel;
import cn.com.code.admin.service.IPermissionUrlService;
import cn.com.code.common.bean.CommonException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *　　
 *   PermissionUrl 控制器    
 *
 *   @author 55555
 *   @since 2020-04-24
 */

@RestController
@Api(tags="PermissionUrlController 控制器")
public class PermissionUrlController implements IPermissionUrlController {
    private final Logger logger = LoggerFactory.getLogger(PermissionUrlController.class);

    @Autowired
    public IPermissionUrlService permissionUrlService;

    /**
     * 获取分页列表
     *
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @Override
    @ApiOperation(value="获取分页列表  55555", notes="获取分页列表  55555", response = PermissionUrlModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页大小", required = true, dataType = "int", defaultValue = "10"),
        @ApiImplicitParam(paramType="query", name = "pageNumber", value = "页数", required = true, dataType = "int", defaultValue = "1")
    })
    @GetMapping("/permissionUrl")
    public Page<PermissionUrlModel> selectPage(@ModelAttribute PermissionUrlModel permissionUrlModel, Integer pageSize, Integer pageNumber) {
        if (pageSize == null || pageNumber == null) {
            throw new CommonException("缺少必要的分页参数！");
        }
        Page<PermissionUrlModel> page = new Page<PermissionUrlModel>(pageNumber, pageSize);
        Wrapper<PermissionUrlModel> wrapper = new EntityWrapper<PermissionUrlModel>(permissionUrlModel);
        permissionUrlService.selectPage(page, wrapper);
        return page;
    }

	/**
     * 获取列表
     *
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @Override
    @ApiOperation(value="获取列表  55555", notes="获取列表  55555", response = PermissionUrlModel.class)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("/permissionUrlList")
    public List<PermissionUrlModel> selectList(@ModelAttribute PermissionUrlModel permissionUrlModel) {

        Wrapper<PermissionUrlModel> wrapper = new EntityWrapper<PermissionUrlModel>(permissionUrlModel);
        return permissionUrlService.selectList(wrapper);

    }

     /**
     * 添加
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @Override
    @ApiOperation(value = "添加  55555", notes = "添加PermissionUrl 55555", response = PermissionUrlModel.class)
    @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    @PostMapping("/permissionUrl")
    public Boolean insert(@ModelAttribute PermissionUrlModel permissionUrlModel) {

        return permissionUrlService.insert(permissionUrlModel);

        }

    /**
     * 修改
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @Override
    @ApiOperation(value="修改  55555", notes="更新PermissionUrl 55555", response = PermissionUrlModel.class)
    @ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String")
    @PutMapping("/permissionUrl")
    public Boolean updateById(@RequestBody PermissionUrlModel permissionUrlModel) {

        return permissionUrlService.updateById(permissionUrlModel);

    }

    /**
     * 通过id获取详情
     *
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @Override
    @ApiOperation(value="通过id获取详情  55555", notes="通过id获取详情  55555", response = PermissionUrlModel.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
    	@ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "String", required = true)
    })
    @GetMapping("/permissionUrl/{id}")
    public PermissionUrlModel selectById(@PathVariable String id) {

        return permissionUrlService.selectById(id);

    }

    /**
     * 通过id删除数据
     *
     * @author : 55555
     * @since : Create in 2020-04-24
     */
    @Override
    @ApiOperation(value="通过id删除数据  55555", notes="通过id删除数据  55555", response = PermissionUrlModel.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "accessToken", value = "令牌", required = true, dataType = "String"),
     	@ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "String", required = true)
    })
    @DeleteMapping("/permissionUrl/{id}")
    public Boolean deleteById(@PathVariable String id) {

        return permissionUrlService.deleteById(id);
    }
}

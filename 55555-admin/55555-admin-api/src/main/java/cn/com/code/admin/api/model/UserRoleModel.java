package cn.com.code.admin.api.model;

import cn.com.code.admin.api.pojo.UserRole;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *  扩展对象 注：扩展字段需要添加 @TableField(exist = false) 标签表明为非数据库字段
 *
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 * @description
 */
@Data
@TableName("TB_USER_ROLE")
@ApiModel(value = "")
public class UserRoleModel extends UserRole {


}

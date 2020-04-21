package cn.com.code.admin.api.model;
import cn.com.code.admin.api.pojo.PermissionUrl;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
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
@TableName("TB_PERMISSION_URL")
@ApiModel(value = "权限表")
public class PermissionUrlModel extends PermissionUrl {

}

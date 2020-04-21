package cn.com.code.admin.api.pojo;

    import com.baomidou.mybatisplus.annotations.TableId;
    import com.baomidou.mybatisplus.annotations.TableField;
    import com.baomidou.mybatisplus.activerecord.Model;
    import com.baomidou.mybatisplus.annotations.TableName;
    import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 实体类  与数据库一一对应,请勿轻易修改
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 * @description
 */
@Data
@ApiModel(value = "TB_PERMISSION_URL", description = "")
public class PermissionUrl extends Model<PermissionUrl> {

private static final long serialVersionUID = 1L;

    public static final String ID = "ID";

    public static final String ROLE = "ROLE";

    public static final String METHOD = "METHOD";

    public static final String URL = "URL";

    public static final String IS_DELETE = "IS_DELETE";


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId("ID")
	private String id;

    /**
     * 角色key
     */
    @ApiModelProperty(value = "角色key")
    @TableField("ROLE")
	private String role;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    @TableField("METHOD")
	private String method;

    /**
     * URL
     */
    @ApiModelProperty(value = "URL")
    @TableField("URL")
	private String url;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    @TableField("IS_DELETE")
	private String isDelete;



	@Override
	protected Serializable pkVal() {
            return this.id;
	}


}

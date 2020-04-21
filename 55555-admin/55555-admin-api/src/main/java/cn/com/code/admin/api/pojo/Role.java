package cn.com.code.admin.api.pojo;

    import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

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
@ApiModel(value = "TB_ROLE", description = "")
public class Role extends Model<Role> {

private static final long serialVersionUID = 1L;

    public static final String ROLE_ID = "ROLE_ID";

    public static final String ROLE_NAME = "ROLE_NAME";

    public static final String IS_DELETE = "IS_DELETE";

    public static final String REMARK = "REMARK";

    public static final String PARENT_ID = "PARENT_ID";


    @TableId("ROLE_ID")
	private String roleId;

    @TableField("ROLE_NAME")
	private String roleName;

    @TableField("IS_DELETE")
	private String isDelete;

    @TableField("REMARK")
	private String remark;

    @TableField("PARENT_ID")
	private String parentId;



	@Override
	protected Serializable pkVal() {
            return this.roleId;
	}


}

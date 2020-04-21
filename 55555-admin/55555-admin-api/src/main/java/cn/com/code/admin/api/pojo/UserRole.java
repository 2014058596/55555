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
@ApiModel(value = "TB_USER_ROLE", description = "")
public class UserRole extends Model<UserRole> {

private static final long serialVersionUID = 1L;

    public static final String ID = "ID";

    public static final String ROLE_ID = "ROLE_ID";

    public static final String USER_ID = "USER_ID";

    public static final String IS_DELETE = "IS_DELETE";


    @TableId("ID")
	private String id;

    @TableField("ROLE_ID")
	private String roleId;

    @TableField("USER_ID")
	private String userId;

    @TableField("IS_DELETE")
	private String isDelete;



	@Override
	protected Serializable pkVal() {
            return this.id;
	}


}

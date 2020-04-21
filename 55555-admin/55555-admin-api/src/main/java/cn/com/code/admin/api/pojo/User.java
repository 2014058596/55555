package cn.com.code.admin.api.pojo;

    import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表实体类  与数据库一一对应,请勿轻易修改
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 * @description
 */
@Data
@ApiModel(value = "TB_USER", description = "用户表")
public class User extends Model<User> {

private static final long serialVersionUID = 1L;

    public static final String USER_ID = "USER_ID";

    public static final String USER_NAME = "USER_NAME";

    public static final String TELEPHONE = "TELEPHONE";

    public static final String PASSWORD = "PASSWORD";

    public static final String STATUS = "STATUS";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String IS_DELETE = "IS_DELETE";


    /**
     * userid
     */
    @ApiModelProperty(value = "userid")
    @TableId("USER_ID")
	private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("USER_NAME")
	private String userName;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @TableField("TELEPHONE")
	private String telephone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
	private String password;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
	private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
	private Date createTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    @TableField("IS_DELETE")
	private String isDelete;



	@Override
	protected Serializable pkVal() {
            return this.userId;
	}


}

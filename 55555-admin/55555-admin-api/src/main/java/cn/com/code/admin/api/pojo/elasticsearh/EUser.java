package cn.com.code.admin.api.pojo.elasticsearh;

import com.frameworkset.orm.annotation.ESId;
import com.frameworkset.orm.annotation.ESIndex;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: EUser
 * @Description: TODO
 * @author: 55555
 * @date: 2020年05月15日 12:09 上午
 */
@Data
@ESIndex(name = "user", type = "user")
public class EUser {

    /**
     * userid
     */
    @ESId(readSet = true,persistent = true)
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */

    private String isDelete;

    /**
     *姓名
     */
    private String name;
}

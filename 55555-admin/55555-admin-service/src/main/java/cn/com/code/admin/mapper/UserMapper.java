package cn.com.code.admin.mapper;

import cn.com.code.admin.api.model.UserModel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 55555
 * @version 2020-04-21
 */
public interface UserMapper extends BaseMapper<UserModel> {

    /**
     * 查询用户信息包含角色
     * @param wrapper
     * @return
     */
    List<UserModel> selectUserModel(@Param("ew") Wrapper<UserModel> wrapper);

}
package cn.com.code.admin.service.security;

import cn.com.code.admin.api.model.RoleModel;
import cn.com.code.admin.api.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SecurityUserDetails
 * @Description: TODO
 * @author: 55555
 * @date: 2020年04月21日 10:53 上午
 */
public class SecurityUserDetails implements UserDetails {

    private UserModel userModel;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleModel> roleModels = userModel.getRoleModels();
        if(roleModels == null || roleModels.isEmpty()){
            return null;
        }
        return roleModels.stream()
                .map(roleModel -> new SimpleGrantedAuthority("ROLE_" + roleModel.getRoleId()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public SecurityUserDetails(UserModel userModel){
        this.userModel = userModel;
    }
}

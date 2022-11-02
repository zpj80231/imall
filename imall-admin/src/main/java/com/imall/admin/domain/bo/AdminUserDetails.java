package com.imall.admin.domain.bo;

import com.imall.mbg.domain.UmsAdminEntity;
import com.imall.mbg.domain.UmsResourceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDetails implements UserDetails {

    private static final long serialVersionUID = 524355064004149533L;
    /**
     * 用户
     */
    private UmsAdminEntity umsAdminEntity;
    /**
     * 权限
     */
    private List<UmsResourceEntity> resourceList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 添加用户权限
        return resourceList.stream().map(r -> new SimpleGrantedAuthority("Permission:" + r.getId())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdminEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdminEntity.getUsername();
    }

    /**
     * 判断用户是否过期 为true代表不过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否帐户未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     */
    @Override
    public boolean isEnabled() {
        return umsAdminEntity.getStatus().equals(1);
    }
}

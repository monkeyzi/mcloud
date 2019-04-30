package com.monkeyzi.mcloud.auth.service;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class McloudUser extends User {

    /**
     * 用户ID
     */
    @Getter
    private Integer id;

    /**
     * 部门ID
     */
    @Getter
    private Integer deptId;

    /**
     * 租户ID
     */
    @Getter
    private Integer tenantId;


    public McloudUser(Integer id,
                      Integer deptId,
                      Integer tenantId,
                      String username,
                      String password,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
        this.tenantId = tenantId;
    }
}

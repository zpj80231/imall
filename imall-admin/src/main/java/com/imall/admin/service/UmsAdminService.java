package com.imall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imall.admin.domain.UmsAdminEntity;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author zhangpengjun
 * @date 2022/8/24
 */
public interface UmsAdminService extends IService<UmsAdminEntity>{

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 根据用户名获取用户详情
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);
}

package com.imall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imall.mbg.domain.UmsAdminEntity;
import com.imall.mbg.domain.UmsResourceEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
public interface UmsAdminService extends IService<UmsAdminEntity> {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 根据用户名获取用户详情
     *
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);

    List<UmsResourceEntity> getResourceList(Long id);
}
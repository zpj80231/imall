package com.imall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imall.mbg.domain.UmsAdminEntity;
import com.imall.mbg.domain.UmsResourceEntity;
import com.imall.mbg.domain.UmsRoleEntity;
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

    /**
     * 获得资源列表
     *
     * @param id id
     * @return {@link List}<{@link UmsResourceEntity}>
     */
    List<UmsResourceEntity> getResourceList(Long id);

    /**
     * 刷新令牌
     *
     * @param token 令牌
     * @return {@link String}
     */
    String refreshToken(String token);

    /**
     * 获取用户通过用户名
     *
     * @param username 用户名
     * @return {@link UmsAdminEntity}
     */
    UmsAdminEntity getAdminByUsername(String username);

    /**
     * 获取角色列表
     *
     * @param adminId adminId
     * @return {@link List}<{@link UmsRoleEntity}>
     */
    List<UmsRoleEntity> getRoleList(Long adminId);

    /**
     * 用户列表分页
     *
     * @param keyword  关键字
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @return {@link List}<{@link UmsAdminEntity}>
     */
    List<UmsAdminEntity> findPage(String keyword, Integer pageNum, Integer pageSize);
}

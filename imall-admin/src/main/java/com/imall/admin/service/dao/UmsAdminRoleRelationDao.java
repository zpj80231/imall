package com.imall.admin.service.dao;

import com.imall.mbg.domain.UmsResourceEntity;
import com.imall.mbg.domain.UmsRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/9/2
 */
public interface UmsAdminRoleRelationDao {

    /**
     * 获得资源列表
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link UmsResourceEntity}>
     */
    List<UmsResourceEntity> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取角色列表
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link UmsRoleEntity}>
     */
    List<UmsRoleEntity> getRoleList(@Param("adminId") Long adminId);
}

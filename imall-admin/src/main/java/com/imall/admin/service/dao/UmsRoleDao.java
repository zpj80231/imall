package com.imall.admin.service.dao;

import com.imall.mbg.domain.UmsMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/12/20
 */
public interface UmsRoleDao {

    /**
     * 把菜单列表
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link UmsMenuEntity}>
     */
    List<UmsMenuEntity> getMenuList(@Param("adminId") Long adminId);

}

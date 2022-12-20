package com.imall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imall.mbg.domain.UmsMenuEntity;
import com.imall.mbg.domain.UmsRoleEntity;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/8/30
 */
public interface UmsRoleService extends IService<UmsRoleEntity> {

    /**
     * 把菜单列表
     *
     * @param adminId 管理员id
     * @return {@link UmsMenuEntity}
     */
    List<UmsMenuEntity> getMenuList(Long adminId);

}

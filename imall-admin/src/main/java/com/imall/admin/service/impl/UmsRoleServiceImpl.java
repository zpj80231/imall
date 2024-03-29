package com.imall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imall.admin.service.UmsRoleService;
import com.imall.admin.service.dao.UmsRoleDao;
import com.imall.mbg.domain.UmsMenuEntity;
import com.imall.mbg.domain.UmsRoleEntity;
import com.imall.mbg.mapper.UmsRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/8/30
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRoleEntity> implements UmsRoleService {

    @Autowired
    private UmsRoleDao roleDao;

    @Override
    public List<UmsMenuEntity> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }
}

package com.imall.admin.service.dao;

import com.imall.mbg.domain.UmsResourceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/9/2
 */
public interface UmsAdminRoleRelationDao {

    List<UmsResourceEntity> getResourceList(@Param("adminId") Long adminId);

}

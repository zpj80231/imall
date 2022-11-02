package com.imall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imall.admin.domain.bo.AdminUserDetails;
import com.imall.admin.service.UmsAdminService;
import com.imall.admin.service.dao.UmsAdminRoleRelationDao;
import com.imall.admin.util.JwtTokenUtil;
import com.imall.common.exception.ApiException;
import com.imall.common.exception.Asserts;
import com.imall.mbg.domain.UmsAdminEntity;
import com.imall.mbg.domain.UmsResourceEntity;
import com.imall.mbg.mapper.UmsAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdminEntity> implements UmsAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Override
    public String login(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        // 校验用户
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            Asserts.fail("密码错误，请重试");
        }
        if (!userDetails.isEnabled()) {
            Asserts.fail("帐号被锁定，请联系管理员");
        }
        // 认证通过，用户信息放入redis
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LambdaQueryWrapper<UmsAdminEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UmsAdminEntity::getUsername, username);
        UmsAdminEntity adminEntity = getOne(queryWrapper, true);
        if (adminEntity == null) {
            throw new ApiException("用户名或密码错误");
        }
        return new AdminUserDetails(adminEntity, getResourceList(adminEntity.getId()));
    }

    @Override
    public List<UmsResourceEntity> getResourceList(Long id) {
        List<UmsResourceEntity> resourceList = umsAdminRoleRelationDao.getResourceList(id);
        return resourceList;
    }
}

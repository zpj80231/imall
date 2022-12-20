package com.imall.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imall.admin.domain.bo.AdminUserDetails;
import com.imall.admin.domain.dto.TokenDto;
import com.imall.admin.domain.dto.UmsAdminLoginDto;
import com.imall.admin.service.UmsAdminService;
import com.imall.admin.service.UmsRoleService;
import com.imall.common.api.CommonPage;
import com.imall.common.api.CommonResult;
import com.imall.mbg.domain.UmsAdminEntity;
import com.imall.mbg.domain.UmsRoleEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Slf4j
@RestController
@Api(value = " 后台用户管理 ", tags = "Ums模块")
@RequestMapping("/user")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsRoleService roleService;

    @GetMapping("/hello")
    @ApiOperation("hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        return CommonResult.success(new TokenDto(refreshToken, tokenHead));
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public CommonResult login(@RequestBody @Validated UmsAdminLoginDto umsAdminLoginDto) {
        String token = adminService.login(umsAdminLoginDto.getUsername(), umsAdminLoginDto.getPassword());
        // todo redis中保存用户登录信息
        return CommonResult.success(new TokenDto(token, tokenHead));
    }

    @PostMapping("/logout")
    @ApiOperation("退出")
    public CommonResult logout() {
        // 从 SecurityContextHolder 获取 当前登录用户
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails loginUser = (AdminUserDetails) authenticationToken.getPrincipal();
        String username = loginUser.getUmsAdminEntity().getUsername();
        // todo 删除redis中用户登录信息
        return CommonResult.success("退出成功");
    }

    @GetMapping("/findPage")
    @ApiOperation(value = "分页查询用户信息", notes = "用户")
    public CommonResult<CommonPage<UmsAdminEntity>> findPage(@RequestParam(required = true, name = "pageNum", defaultValue = "1") Long pageNum,
                                                             @RequestParam(required = true, name = "pageSize", defaultValue = "2") Long pageSize) {
        log.info("pageNum: [{}], pageSize: [{}]", pageNum, pageSize);
        Page<UmsAdminEntity> page = new Page<>(pageNum, pageSize);
        adminService.page(page);
        CommonPage<UmsAdminEntity> restPage = CommonPage.restPage(page);
        return CommonResult.success(restPage);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdminEntity umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRoleEntity> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRoleEntity::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return CommonResult.success(data);
    }

}

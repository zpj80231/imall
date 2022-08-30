package com.imall.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imall.admin.bo.AdminUserDetails;
import com.imall.admin.dto.TokenDto;
import com.imall.admin.dto.UmsAdminLoginDto;
import com.imall.admin.service.UmsAdminService;
import com.imall.common.api.CommonPage;
import com.imall.common.api.CommonResult;
import com.imall.mbg.domain.UmsAdminEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private UmsAdminService umsAdminService;

    @GetMapping("/hello")
    @ApiOperation("hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public TokenDto login(@RequestBody @Validated UmsAdminLoginDto umsAdminLoginDto) {
        String token = umsAdminService.login(umsAdminLoginDto.getUsername(), umsAdminLoginDto.getPassword());
        // todo redis中保存用户登录信息
        return new TokenDto(token, tokenHead);
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

    @GetMapping("/list")
    @ApiOperation(value = "查询所有用户信息", notes = "用户信息")
    public CommonResult<List<UmsAdminEntity>> findAll() {
        List<UmsAdminEntity> list = umsAdminService.list();
        return CommonResult.success(list);
    }

    @GetMapping("/findPage")
    @ApiOperation(value = "分页查询用户信息", notes = "用户")
    public CommonResult<CommonPage<UmsAdminEntity>> findPage(@RequestParam(required = true, name = "pageNum", defaultValue = "1") Long pageNum,
                                                             @RequestParam(required = true, name = "pageSize", defaultValue = "2") Long pageSize) {
        log.info("pageNum: [{}], pageSize: [{}]", pageNum, pageSize);
        Page<UmsAdminEntity> page = new Page<>(pageNum, pageSize);
        umsAdminService.page(page);
        CommonPage<UmsAdminEntity> restPage = CommonPage.restPage(page);
        return CommonResult.success(restPage);
    }

}

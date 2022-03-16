package com.zpj80231.imall.mbg.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zpj80231.imall.common.api.CommonPage;
import com.zpj80231.imall.common.api.CommonResult;
import com.zpj80231.imall.mbg.modules.ums.entity.UmsAdmin;
import com.zpj80231.imall.mbg.modules.ums.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台用户表 前端控制器
 *
 * @author zpj80231
 * @since 2020-09-29
 */
@RestController
@RequestMapping("/ums/umsAdmin")
@Api(value = " 后台用户管理 ", tags = "Ums模块")
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;

    @GetMapping("/list")
    @ApiOperation(value = "查询所有用户信息", notes = "用户信息")
    public CommonResult<List<UmsAdmin>> findAll() {
        List<UmsAdmin> list = umsAdminService.list();
        return CommonResult.success(list);
    }

    @GetMapping("/findPage")
    @ApiOperation(value = "分页查询用户信息", notes = "用户")
    public CommonResult<CommonPage<UmsAdmin>> findPage(@RequestParam(required = true, name = "pageNum", defaultValue = "1") Long pageNum,
                                                       @RequestParam(required = true, name = "pageSize", defaultValue = "2") Long pageSize) {
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        umsAdminService.page(page);
        CommonPage<UmsAdmin> restPage = CommonPage.restPage(page);
        return CommonResult.success(restPage);
    }

}


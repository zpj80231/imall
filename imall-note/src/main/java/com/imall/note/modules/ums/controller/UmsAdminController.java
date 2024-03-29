package com.imall.note.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imall.common.api.CommonPage;
import com.imall.common.api.CommonResult;
import com.imall.note.modules.ums.bo.UmsAdminBo;
import com.imall.note.modules.ums.entity.UmsAdmin;
import com.imall.note.modules.ums.service.UmsAdminService;
import com.imall.note.util.excel.EasyExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  后台用户表 前端控制器
 * </p>
 *
 * @author zhangpengjun
 * @date 2022-04-01
 */
@RestController
@RequestMapping("/ums/umsAdmin")
@Api(value = " 后台用户管理 ", tags = "Ums模块")
@Slf4j
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
        log.info("pageNum: [{}], pageSize: [{}]", pageNum, pageSize);
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        umsAdminService.page(page);
        CommonPage<UmsAdmin> restPage = CommonPage.restPage(page);
        return CommonResult.success(restPage);
    }

    @GetMapping(value = "/downloadEasyExcel",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "用户列表下载", notes = "用户列表下载")
    public void downloadEasyExcel(HttpServletResponse response) throws IOException {
        List<UmsAdmin> list = umsAdminService.list();
        List<UmsAdminBo> umsAdminBoList = list.stream().map(entity -> {
            UmsAdminBo umsAdminBo = new UmsAdminBo();
            BeanUtils.copyProperties(entity, umsAdminBo);
            return umsAdminBo;
        }).collect(Collectors.toList());
        EasyExcelUtil.write(response, "用户列表", umsAdminBoList);
    }

}


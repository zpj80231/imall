package com.imall.note.modules.ums.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.imall.note.util.excel.DateStringConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zhangpengjun
 * @date 2022/4/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmsAdminBo对象", description="EasyExcel文件对象")
public class UmsAdminBo {

    @ApiModelProperty(value = "用户名")
    @ExcelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @ExcelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    @ExcelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    @ExcelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    @ExcelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    @ExcelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间", converter = DateStringConverter.class)
    @DateTimeFormat("yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    @ExcelProperty(value = "最后登录时间", converter = DateStringConverter.class)
    @DateTimeFormat("yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    @ApiModelProperty(value = "帐号启用状态：0-> 禁用；1-> 启用")
    @ExcelProperty(value = "帐号启用状态")
    private Integer status;

}

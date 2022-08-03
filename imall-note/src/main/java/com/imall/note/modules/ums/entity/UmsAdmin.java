package com.imall.note.modules.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  后台用户表
 * </p>
 *
 * @author zhangpengjun
 * @date 2022-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_admin")
@ApiModel(value="UmsAdmin对象", description=" 后台用户表")
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    @ApiModelProperty(value = " 头像")
    private String icon;

    @ApiModelProperty(value = " 邮箱")
    private String email;

    @ApiModelProperty(value = " 昵称")
    private String nickName;

    @ApiModelProperty(value = " 备注信息")
    private String note;

    @ApiModelProperty(value = " 创建时间")
    private Date createTime;

    @ApiModelProperty(value = " 最后登录时间")
    private Date loginTime;

    @ApiModelProperty(value = " 帐号启用状态：0-> 禁用；1-> 启用")
    private Integer status;


}

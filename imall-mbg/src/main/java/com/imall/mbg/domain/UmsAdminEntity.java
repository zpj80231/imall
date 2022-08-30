package com.imall.mbg.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author zhangpengjun
 * @date 2022/8/30
 */
/**
    * 后台用户表
    */
@ApiModel(value="com-imall-mbg-domain-UmsAdminEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ums_admin")
public class UmsAdminEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "username")
    @ApiModelProperty(value="")
    private String username;

    @TableField(value = "password")
    @ApiModelProperty(value="")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "icon")
    @ApiModelProperty(value="头像")
    private String icon;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="邮箱")
    private String email;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    @ApiModelProperty(value="昵称")
    private String nickName;

    /**
     * 备注信息
     */
    @TableField(value = "note")
    @ApiModelProperty(value="备注信息")
    private String note;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @TableField(value = "login_time")
    @ApiModelProperty(value="最后登录时间")
    private Date loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    @TableField(value = "status")
    @ApiModelProperty(value="帐号启用状态：0->禁用；1->启用")
    private Integer status;

    private static final long serialVersionUID = 1L;
}
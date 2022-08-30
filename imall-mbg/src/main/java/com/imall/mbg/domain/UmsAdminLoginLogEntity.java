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
    * 后台用户登录日志表
    */
@ApiModel(value="com-imall-mbg-domain-UmsAdminLoginLogEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ums_admin_login_log")
public class UmsAdminLoginLogEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "admin_id")
    @ApiModelProperty(value="")
    private Long adminId;

    @TableField(value = "create_time")
    @ApiModelProperty(value="")
    private Date createTime;

    @TableField(value = "ip")
    @ApiModelProperty(value="")
    private String ip;

    @TableField(value = "address")
    @ApiModelProperty(value="")
    private String address;

    /**
     * 浏览器登录类型
     */
    @TableField(value = "user_agent")
    @ApiModelProperty(value="浏览器登录类型")
    private String userAgent;

    private static final long serialVersionUID = 1L;
}
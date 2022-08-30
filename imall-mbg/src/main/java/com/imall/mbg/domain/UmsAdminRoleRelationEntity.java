package com.imall.mbg.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author zhangpengjun
 * @date 2022/8/30
 */
/**
    * 后台用户和角色关系表
    */
@ApiModel(value="com-imall-mbg-domain-UmsAdminRoleRelationEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ums_admin_role_relation")
public class UmsAdminRoleRelationEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    @TableField(value = "admin_id")
    @ApiModelProperty(value="")
    private Long adminId;

    @TableField(value = "role_id")
    @ApiModelProperty(value="")
    private Long roleId;

    private static final long serialVersionUID = 1L;
}
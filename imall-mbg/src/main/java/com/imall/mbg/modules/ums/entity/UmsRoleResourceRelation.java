package com.imall.mbg.modules.ums.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  后台角色资源关系表
 * </p>
 *
 * @author zpj80231
 * @since 2020-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_role_resource_relation")
@ApiModel(value="UmsRoleResourceRelation对象", description=" 后台角色资源关系表")
public class UmsRoleResourceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = " 角色 ID")
    private Long roleId;

    @ApiModelProperty(value = " 资源 ID")
    private Long resourceId;


}

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
    * 后台资源表
    */
@ApiModel(value="com-imall-mbg-domain-UmsResourceEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ums_resource")
public class UmsResourceEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 资源名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="资源名称")
    private String name;

    /**
     * 资源URL
     */
    @TableField(value = "url")
    @ApiModelProperty(value="资源URL")
    private String url;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 资源分类ID
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value="资源分类ID")
    private Long categoryId;

    private static final long serialVersionUID = 1L;
}
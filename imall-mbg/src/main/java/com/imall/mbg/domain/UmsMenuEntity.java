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
    * 后台菜单表
    */
@ApiModel(value="com-imall-mbg-domain-UmsMenuEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ums_menu")
public class UmsMenuEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 父级ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value="父级ID")
    private Long parentId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 菜单名称
     */
    @TableField(value = "title")
    @ApiModelProperty(value="菜单名称")
    private String title;

    /**
     * 菜单级数
     */
    @TableField(value = "level")
    @ApiModelProperty(value="菜单级数")
    private Integer level;

    /**
     * 菜单排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="菜单排序")
    private Integer sort;

    /**
     * 前端名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="前端名称")
    private String name;

    /**
     * 前端图标
     */
    @TableField(value = "icon")
    @ApiModelProperty(value="前端图标")
    private String icon;

    /**
     * 前端隐藏
     */
    @TableField(value = "hidden")
    @ApiModelProperty(value="前端隐藏")
    private Integer hidden;

    private static final long serialVersionUID = 1L;
}
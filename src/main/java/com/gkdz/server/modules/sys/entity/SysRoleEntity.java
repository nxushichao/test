package com.gkdz.server.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 */
@Data
@TableName("sys_role")
@ApiModel(value = "SysRoleEntity", description = "角色")
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者ID")
    private Long createUserId;

    @TableField(exist = false)
    private List<Long> menuIdList;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否为管理者(0:是,1:否)", dataType = "String")
    @NotBlank(message = "是否部门管理者不能为空")
    private String master;

    @ApiModelProperty(value = "所属组织id")
    @TableField(exist = false)
    private Long orgId;

    @ApiModelProperty(value = "所属组织名称")
    @TableField(exist = false)
    private String orgName;
}

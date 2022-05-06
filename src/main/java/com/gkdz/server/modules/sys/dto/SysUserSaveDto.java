package com.gkdz.server.modules.sys.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author wu
 */
@Data
@ApiModel(value = "SysUser对象", description = "系统用户")
public class SysUserSaveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("状态  0：禁用   1：正常")
    private Integer status;

    @ApiModelProperty("创建者ID")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改者")
    private Long updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("删除:0=未删除,1=已删除")
    private Integer deleted;

    @ApiModelProperty("照片名")
    private String photo;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("技术级别")
    private String level;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("领导")
    @TableField("leader")
    private Long leader;

    @ApiModelProperty("角色列表")
    private List<Long> roleIdList;

    @ApiModelProperty("组织列表")
    private List<Long> orgIdList;
}

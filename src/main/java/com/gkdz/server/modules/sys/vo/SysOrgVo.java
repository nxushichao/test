package com.gkdz.server.modules.sys.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gkdz.server.common.validator.group.AddGroup;
import com.gkdz.server.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author wu
 */
@Data
@TableName("sys_org")
@ApiModel(value = "SysOrg对象", description = "")
public class SysOrgVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织机构id")
    private Long id;

    @NotBlank(message = "组织机构名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "组织机构名称")
    private String orgName;

    @ApiModelProperty(value = "组织机构简介")
    private String orgDesc;

    @NotNull(message = "上级组织机构不能为空", groups = {AddGroup.class})
    @ApiModelProperty(value = "上级组织机构id")
    private Long parentOrgId;

    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private Long updateUser;

    @ApiModelProperty(value = "创建时间")
    private Date updateTime;

    @ApiModelProperty(value = "展示:0=不展示,1=展示")
    private Integer show;

    @ApiModelProperty(value = "级别")
    private Integer orgLevel;

    private List<SysOrgVo> list;
}

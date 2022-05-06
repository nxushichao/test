package com.gkdz.server.modules.sys.vo;

import com.gkdz.server.common.validator.group.AddGroup;
import com.gkdz.server.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @author wu
 */
@Data
@ApiModel(value = "SysOrg对象", description = "")
public class SysOrgInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织机构id")
    private Long id;

    @NotBlank(message = "组织机构名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "组织机构名称")
    private String orgName;

    @ApiModelProperty(value = "组织机构简介")
    private String orgDesc;

    @ApiModelProperty(value = "上级组织机构id")
    private Long parentOrgId;

    @ApiModelProperty(value = "上级组织机构名称")
    private String parentOrgName;

    @ApiModelProperty(value = "展示:0=不展示,1=展示")
    private Integer show;

    @ApiModelProperty(value = "级别")
    private Integer orgLevel;

}

package com.gkdz.server.modules.sys.dto;

import com.gkdz.server.modules.sys.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu
 * @date 2022/1/10 14:05
 */
@Data
public class SysUserListDto {
    @ApiModelProperty("分页参数")
    private BasePage basePage;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("角色")
    private Long roleId;

    @ApiModelProperty("组织")
    private Long orgId;

    @ApiModelProperty("状态:0=停用,1=启用")
    private Integer status;
}

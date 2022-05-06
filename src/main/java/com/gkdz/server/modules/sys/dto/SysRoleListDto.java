package com.gkdz.server.modules.sys.dto;

import com.gkdz.server.modules.sys.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu
 * @date 2022/1/10 14:05
 */
@Data
public class SysRoleListDto {
    @ApiModelProperty("分页参数")
    private BasePage basePage;

    @ApiModelProperty("角色名")
    private String roleName;
}

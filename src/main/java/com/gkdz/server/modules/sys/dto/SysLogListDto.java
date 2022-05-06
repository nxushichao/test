package com.gkdz.server.modules.sys.dto;

import com.gkdz.server.modules.sys.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu
 * @date 2022/1/10 14:05
 */
@Data
public class SysLogListDto {
    @ApiModelProperty("分页参数")
    private BasePage basePage;

    @ApiModelProperty("用户名")
    private String username;
}

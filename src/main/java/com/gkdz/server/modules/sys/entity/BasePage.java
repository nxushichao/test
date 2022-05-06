package com.gkdz.server.modules.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author wu
 * @date 2021/10/27 12:18
 */
@Data
@ApiModel(value = "BasePage对象", description = "基础分页信息")
public class BasePage {
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Long page;

    @ApiModelProperty(value = "条数", required = true, example = "10")
    private Long limit;

    @ApiModelProperty(value = "排序字段")
    private String orderField;

    @ApiModelProperty(value = "排序方式")
    private String order;
}

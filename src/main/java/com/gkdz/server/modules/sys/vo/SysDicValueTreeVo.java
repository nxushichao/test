package com.gkdz.server.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gkdz.server.modules.sys.entity.SysDicValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/20 8:28
 */
@Data
@ApiModel(value = "SysDicValueTree", description = "字典值树形")
public class SysDicValueTreeVo extends SysDicValue {
    @ApiModelProperty("子级")
    private List<SysDicValueTreeVo> child;

    @ApiModelProperty("是否有子级")
    private boolean hasChild;

    @ApiModelProperty("展示名称")
    @JsonIgnore
    private String showName;
}

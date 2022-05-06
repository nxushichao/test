package com.gkdz.server.modules.dev.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.gkdz.server.modules.sys.entity.BasePage;


/**
* 分页查询对象
*
* @author 14651935
* @since 2022-05-05
*/
@Data
@ApiModel(value = " 分页查询对象")
public class TestPageDto  {

    @ApiModelProperty("分页参数")
    private BasePage basePage;


}


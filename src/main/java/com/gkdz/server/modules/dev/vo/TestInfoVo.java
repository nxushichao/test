package com.gkdz.server.modules.dev.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gkdz.server.modules.dev.entity.Test;

/**
* 
*
* @author 14651935
* @since 2022-05-05
*/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "Test ", description = "")
public class TestInfoVo extends Test {

}


package com.gkdz.server.modules.dev.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gkdz.server.modules.dev.entity.Test;

/**
* 保存对象
*
* @author 14651935
* @since 2022-05-05
*/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Test 保存对象", description = "保存对象")
public class TestSaveDto extends Test {

}


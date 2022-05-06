package com.gkdz.server.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统验证码
 *
 * @author wu
 */
@Data
@TableName("sys_captcha")
@ApiModel(value = "SysCaptcha对象", description = "系统验证码")
public class SysCaptchaEntity {
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;
}

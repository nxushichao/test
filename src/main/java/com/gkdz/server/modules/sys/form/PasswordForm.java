package com.gkdz.server.modules.sys.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 密码表单
 *
 * @author wu
 */
@Data
@ApiModel(value = "PasswordForm", description = "用户类")
public class PasswordForm {
    /**
     * 原密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}

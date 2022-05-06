package com.gkdz.server.modules.sys.controller;

import com.gkdz.server.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * Controller公共组件
 *
 * @author wu
 */
public abstract class AbstractController {
    protected SysUserEntity getUser() {

        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }
}

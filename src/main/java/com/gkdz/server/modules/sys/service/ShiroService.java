package com.gkdz.server.modules.sys.service;

import com.gkdz.server.modules.sys.entity.SysUserEntity;
import com.gkdz.server.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author wu
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 根据token查询用户token信息
     *
     * @param token token
     * @return 用户token信息
     */
    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserEntity queryUser(Long userId);
}

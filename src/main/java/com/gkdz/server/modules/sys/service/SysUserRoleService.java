package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import com.gkdz.server.modules.sys.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author Mark
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    /**
     * 根据用户ID查找该用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleEntity> queryRoleByUserId(Long userId);

    /**
     * 保存或者更新用户与角色关系
     *
     * @param userId     用户ID
     * @param roleIdList 角色ID列表
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds 角色ID数组
     * @return 删除数量
     */
    int deleteBatch(Long[] roleIds);
}

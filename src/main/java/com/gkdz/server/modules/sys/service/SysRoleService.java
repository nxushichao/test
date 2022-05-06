package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.modules.sys.dto.SysRoleListDto;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色
 *
 * @author Mark
 */
public interface SysRoleService extends IService<SysRoleEntity> {
    /**
     * 分页查询
     *
     * @param sysRoleListDto 分页参数
     * @return 分页信息
     */
    PageInfo<SysRoleEntity> queryPage(SysRoleListDto sysRoleListDto);

    /**
     * 保存角色
     *
     * @param role 角色信息
     */
    void saveRole(SysRoleEntity role);

    /**
     * 更新角色
     *
     * @param role 角色信息
     */
    void update(SysRoleEntity role);

    /**
     * 删除角色
     *
     * @param roleIds 角色ID数组
     */
    void deleteBatch(Long[] roleIds);

    /**
     * 查询用户创建的角色ID列表
     *
     * @param createUserId 用户ID
     * @return 创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
}

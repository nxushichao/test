

package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author Mark
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    /**
     * 保存或更新角色与菜单关系
     *
     * @param roleId     角色ID
     * @param menuIdList 菜单列表
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     *
     * @param roleIds 角色ID数组
     * @return 删除数量
     */
    int deleteBatch(Long[] roleIds);

}

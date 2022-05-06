package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Mark
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     * @return 子菜单集合
     */
    List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     * @return 子菜单集合
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     *
     * @return 子菜单集合
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     *
     * @param userId 用户ID
     * @return 菜单集合
     */
    List<SysMenuEntity> getUserMenuList(Long userId);

    /**
     * 删除
     *
     * @param menuId 菜单ID
     */
    void delete(Long menuId);
}

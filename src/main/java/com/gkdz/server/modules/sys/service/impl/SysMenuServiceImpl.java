package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.common.utils.Constant;
import com.gkdz.server.modules.sys.dao.SysMenuDao;
import com.gkdz.server.modules.sys.entity.SysMenuEntity;
import com.gkdz.server.modules.sys.service.SysMenuService;
import com.gkdz.server.modules.sys.service.SysRoleMenuService;
import com.gkdz.server.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Mark
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private SysRoleMenuService sysRoleMenuService;

  @Override
  public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
    List<SysMenuEntity> menuList = queryListParentId(parentId);
    if (menuIdList == null) {
      return menuList;
    }

    List<SysMenuEntity> userMenuList = new ArrayList<>();
    for (SysMenuEntity menu : menuList) {
      if (menuIdList.contains(menu.getId())) {
        userMenuList.add(menu);
      }
    }
    return userMenuList;
  }

  @Override
  public List<SysMenuEntity> queryListParentId(Long parentId) {
    return baseMapper.queryListParentId(parentId);
  }

  @Override
  public List<SysMenuEntity> queryNotButtonList() {
    return baseMapper.queryNotButtonList();
  }

  @Override
  public List<SysMenuEntity> getUserMenuList(Long userId) {
    // 系统管理员，拥有最高权限
    if (userId == Constant.SUPER_ADMIN) {
      return getMenuList(null);
    }

    // 用户菜单列表
    List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
    return getMenuList(menuIdList);
  }

  /**
   * 获取拥有的菜单列表
   *
   * @param menuIdList
   * @return
   */
  private List<SysMenuEntity> getMenuList(List<Long> menuIdList) {
    // 查询拥有的所有菜单
    List<SysMenuEntity> menus = this.baseMapper.selectList(new QueryWrapper<SysMenuEntity>().lambda().in(Objects.nonNull(menuIdList), SysMenuEntity::getId, menuIdList).in(SysMenuEntity::getType, 0, 1).orderByAsc(SysMenuEntity::getOrderNum));
    // 将id和菜单绑定
    HashMap<Long, SysMenuEntity> menuMap = new HashMap<>(12);
    for (SysMenuEntity s : menus) {
      menuMap.put(s.getId(), s);
    }
    // 使用迭代器,组装菜单的层级关系
    Iterator<SysMenuEntity> iterator = menus.iterator();
    while (iterator.hasNext()) {
      SysMenuEntity menu = iterator.next();
      SysMenuEntity parent = menuMap.get(menu.getParentId());
      if (Objects.nonNull(parent)) {
        parent.getList().add(menu);
        // 将这个菜单从当前节点移除
        iterator.remove();
      }
    }

    return menus;
  }

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public void delete(Long menuId) {
    // 删除菜单
    this.removeById(menuId);
    // 删除菜单与角色关联
    HashMap<String, Object> map = MapUtil.newHashMap();
    map.put("id", menuId);
    sysRoleMenuService.removeByMap(map);
  }

  /**
   * 获取所有菜单列表
   */
  private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
    // 查询根菜单列表
    List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
    // 递归获取子菜单
    getMenuTreeList(menuList, menuIdList);

    return menuList;
  }

  /**
   * 递归
   */
  private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
    List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

    for (SysMenuEntity entity : menuList) {
      // 目录
      if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
        entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
      }
      subMenuList.add(entity);
    }

    return subMenuList;
  }
}

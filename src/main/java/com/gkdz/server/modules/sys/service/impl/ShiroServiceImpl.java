package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gkdz.server.common.utils.Constant;
import com.gkdz.server.modules.sys.dao.*;
import com.gkdz.server.modules.sys.entity.*;
import com.gkdz.server.modules.sys.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author Mark
 */
@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysOrgRoleDao sysOrgRoleDao;


    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        List<Long> orgIds = this.getOrgIdByUserIdWithMaster(userId);
        // 系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
            if (!CollectionUtils.isEmpty(orgIds)) {
                //如果是部门管理者的话，那么默认拥有旗下所有组员的权限
                List<String> menusByOrgIds = this.getMenusByOrgIds(orgIds);
                permsList.addAll(menusByOrgIds);
            }
        }
        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StrUtil.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }


    /**
     * 根据用户id，查询是否为部门管理者
     * 如是，则查询出该用户为管理者的所有部门id
     *
     * @param userId
     * @return
     */
    private List<Long> getOrgIdByUserIdWithMaster(Long userId) {
        List<Long> orgIds = new LinkedList<>();
        List<SysUserRoleEntity> sysUserRoleEntities = sysUserRoleDao.selectList(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId, userId));
        List<Long> ids = new LinkedList<>();
        for (SysUserRoleEntity sysUserRoleEntity : sysUserRoleEntities) {
            ids.add(sysUserRoleEntity.getRoleId());
        }
        if (!CollectionUtils.isEmpty(ids)) {
            List<SysRoleEntity> sysRoleEntities = sysRoleDao.selectList(new QueryWrapper<SysRoleEntity>().lambda().in(SysRoleEntity::getId, ids));
            for (SysRoleEntity sysRoleEntity : sysRoleEntities) {
                if ("0".equals(sysRoleEntity.getMaster())) {
                    SysOrgRoleEntity sysOrgRoleEntity = sysOrgRoleDao.selectOne(new QueryWrapper<SysOrgRoleEntity>().lambda().eq(SysOrgRoleEntity::getRoleId, sysRoleEntity.getId()));
                    if (!ObjectUtils.isEmpty(sysOrgRoleEntity)) {
                        orgIds.add(sysOrgRoleEntity.getOrgId());
                    }
                }
            }
        }

        return orgIds;
    }


    /**
     * 根据部门id集合，查询出部门下所有角色的权限
     *
     * @param orgIds
     * @return
     */
    private List<String> getMenusByOrgIds(List<Long> orgIds) {
        List<String> list = new LinkedList<>();
        List<SysOrgRoleEntity> sysOrgRoleEntityList = sysOrgRoleDao.selectList(new QueryWrapper<SysOrgRoleEntity>().lambda().in(SysOrgRoleEntity::getOrgId, orgIds));
        if (!CollectionUtils.isEmpty(sysOrgRoleEntityList)) {
            List<Long> ids = new LinkedList<>();
            for (SysOrgRoleEntity sysOrgRoleEntity : sysOrgRoleEntityList) {
                ids.add(sysOrgRoleEntity.getRoleId());
            }
            Long[] roleIds = new Long[ids.size()];
            list = sysUserDao.queryPermsByRoleIds(ids.toArray(roleIds));
        }


        return list;
    }
}

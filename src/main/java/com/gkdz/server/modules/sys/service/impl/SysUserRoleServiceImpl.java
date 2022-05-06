package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.modules.sys.dao.SysUserRoleDao;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import com.gkdz.server.modules.sys.entity.SysUserRoleEntity;
import com.gkdz.server.modules.sys.service.SysRoleService;
import com.gkdz.server.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户与角色对应关系
 *
 * @author Mark
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
    @Resource
    private SysRoleService sysRoleService;

    @Override
    public List<SysRoleEntity> queryRoleByUserId(Long userId) {
        List<SysUserRoleEntity> sysUserRoleEntityList = this.list(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId, userId));
        if (!CollectionUtils.isEmpty(sysUserRoleEntityList)) {
            List<SysRoleEntity> sysRoleEntityList = sysRoleService.list(new QueryWrapper<SysRoleEntity>().lambda().in(SysRoleEntity::getId, sysUserRoleEntityList.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toList())));
            return sysRoleEntityList;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        HashMap<String, Object> map = MapUtil.newHashMap();
        map.put("user_id", userId);
        this.removeByMap(map);

        if (roleIdList == null || roleIdList.size() == 0) {
            return;
        }

        //保存用户与角色关系
        for (Long roleId : roleIdList) {
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            this.save(sysUserRoleEntity);
        }
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}

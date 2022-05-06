

package com.gkdz.server.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.modules.sys.dao.SysOrgRoleDao;
import com.gkdz.server.modules.sys.entity.SysOrgRoleEntity;
import com.gkdz.server.modules.sys.service.SysOrgRoleService;
import com.gkdz.server.modules.sys.service.SysRoleService;
import com.gkdz.server.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


/**
 * 组织机构角色
 */
@Service("sysOrgRoleService")
public class SysOrgRoleServiceImpl extends ServiceImpl<SysOrgRoleDao, SysOrgRoleEntity> implements SysOrgRoleService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(Long roleId, Long orgId) {
        SysOrgRoleEntity sysOrgRoleEntity = new SysOrgRoleEntity();
        sysOrgRoleEntity.setRoleId(roleId);
        sysOrgRoleEntity.setOrgId(orgId);

        SysOrgRoleEntity base = this.getOne(new QueryWrapper<SysOrgRoleEntity>().eq("role_id", roleId));
        if (ObjectUtils.isEmpty(base)) {
            this.save(sysOrgRoleEntity);
        } else {
            sysOrgRoleEntity.setId(base.getId());
            this.updateById(sysOrgRoleEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}

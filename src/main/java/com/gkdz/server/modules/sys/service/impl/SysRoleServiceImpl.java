package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.utils.MyPageHelper;
import com.gkdz.server.modules.sys.dao.SysRoleDao;
import com.gkdz.server.modules.sys.dto.SysRoleListDto;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysOrgRoleEntity;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import com.gkdz.server.modules.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 角色
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysOrgRoleService sysOrgRoleService;
    @Autowired
    private SysOrgService sysOrgService;

    @Override
    public PageInfo<SysRoleEntity> queryPage(SysRoleListDto sysRoleListDto) {
        String roleName = sysRoleListDto.getRoleName();

        MyPageHelper.startPage(sysRoleListDto.getBasePage());
        List<SysRoleEntity> list = this.lambdaQuery().like(StrUtil.isNotBlank(roleName), SysRoleEntity::getRoleName, roleName).list();

        PageInfo<SysRoleEntity> pageInfo = new PageInfo<>(list);
        List<SysRoleEntity> records = pageInfo.getList();
        if (!ObjectUtils.isEmpty(records)) {
            for (SysRoleEntity record : records) {
                SysOrgRoleEntity sysOrgRoleEntity = sysOrgRoleService.getOne(new QueryWrapper<SysOrgRoleEntity>().lambda().eq(SysOrgRoleEntity::getRoleId, record.getId()));
                if (!ObjectUtils.isEmpty(sysOrgRoleEntity)) {
                    SysOrgEntity sysOrgEntity = sysOrgService.getById(sysOrgRoleEntity.getOrgId());
                    record.setOrgName(sysOrgEntity.getOrgName());
                    record.setOrgId(sysOrgEntity.getId());
                }
            }
        }
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        // 保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());

        // 保存组织和角色关系
        if (role.getOrgId() != null) {
            sysOrgRoleService.saveOrUpdate(role.getId(), role.getOrgId());
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);

        // 更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());

        // 更新角色和组织的关系
        if (role.getOrgId() != null) {
            sysOrgRoleService.saveOrUpdate(role.getId(), role.getOrgId());
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBatch(Long[] roleIds) {
        // 删除角色
        this.removeByIds(Arrays.asList(roleIds));

        // 删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        // 删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);

        // 删除角色与组织关联
        sysOrgRoleService.deleteBatch(roleIds);
    }

    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }
}

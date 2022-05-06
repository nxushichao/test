package com.gkdz.server.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.annotation.SysLog;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.common.validator.ValidatorUtils;
import com.gkdz.server.modules.sys.dto.SysRoleListDto;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysOrgRoleEntity;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import com.gkdz.server.modules.sys.service.SysOrgRoleService;
import com.gkdz.server.modules.sys.service.SysOrgService;
import com.gkdz.server.modules.sys.service.SysRoleMenuService;
import com.gkdz.server.modules.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 */
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysOrgRoleService sysOrgRoleService;
    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 角色列表
     */
    @ApiOperation("角色列表")
    @PostMapping("/list")
    public R<PageInfo<SysRoleEntity>> list(@RequestBody SysRoleListDto sysRoleListDto) {
        PageInfo<SysRoleEntity> pageInfo = sysRoleService.queryPage(sysRoleListDto);
        return new R<>(pageInfo);
    }

    /**
     * 角色列表
     */
    @ApiOperation("角色列表")
    @GetMapping("/select")
    public R<List<SysRoleEntity>> select() {
        List<SysRoleEntity> list = sysRoleService.list();
        for (SysRoleEntity record : list) {
            SysOrgRoleEntity sysOrgRoleEntity = sysOrgRoleService.getOne(new QueryWrapper<SysOrgRoleEntity>().lambda().eq(SysOrgRoleEntity::getRoleId, record.getId()));
            if (!ObjectUtils.isEmpty(sysOrgRoleEntity)) {
                SysOrgEntity sysOrgEntity = sysOrgService.getById(sysOrgRoleEntity.getOrgId());
                record.setOrgName(sysOrgEntity.getOrgName());
                record.setOrgId(sysOrgEntity.getId());
            }
        }
        return new R<>(list);
    }

    /**
     * 角色信息
     */
    @ApiOperation("角色信息")
    @GetMapping("/info/{roleId}")
    public R<SysRoleEntity> info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);

        // 查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        // 查询角色对应得组织
        SysOrgRoleEntity sysOrgRoleEntity = sysOrgRoleService.getOne(new QueryWrapper<SysOrgRoleEntity>().eq("role_id", role.getId()));
        if (!ObjectUtils.isEmpty(sysOrgRoleEntity)) {
            SysOrgEntity sysOrgEntity = sysOrgService.getById(sysOrgRoleEntity.getOrgId());
            role.setOrgName(sysOrgEntity.getOrgName());
            role.setOrgId(sysOrgEntity.getId());
        }
        return new R<>(role);
    }

    /**
     * 保存角色
     */
    @ApiOperation("保存角色")
    @SysLog("保存角色")
    @PostMapping("/save")
    public R save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.saveRole(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @ApiOperation("修改角色")
    @SysLog("修改角色")
    @PostMapping("/update")
    public R update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    @SysLog("删除角色")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}

package com.gkdz.server.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.annotation.SysLog;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.common.validator.ValidatorUtils;
import com.gkdz.server.common.validator.group.AddGroup;
import com.gkdz.server.common.validator.group.UpdateGroup;
import com.gkdz.server.modules.sys.dto.SysUserListDto;
import com.gkdz.server.modules.sys.dto.SysUserSaveDto;
import com.gkdz.server.modules.sys.entity.SysUserEntity;
import com.gkdz.server.modules.sys.form.PasswordForm;
import com.gkdz.server.modules.sys.service.SysUserService;
import com.gkdz.server.modules.sys.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户
 *
 * @author Mark
 */
@Api(tags = "系统用户接口")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 所有用户列表
     */
    @ApiOperation("所有用户列表")
    @PostMapping("/list")
    public R<PageInfo<SysUserVo>> list(@RequestBody SysUserListDto sysUserListDto) {
        PageInfo<SysUserVo> pageInfo = sysUserService.queryPage(sysUserListDto);
        return new R<>(pageInfo);
    }


    @ApiOperation("管理员重置密码")
    @GetMapping("/reset/{userId}")
    public R reset(@PathVariable("userId") Long userId) {
        sysUserService.reset(userId);
        return R.ok();
    }

    /**
     * 获取登录的用户信息
     */
    @ApiOperation("获取登录的用户信息")
    @GetMapping("/info")
    public R<SysUserEntity> info() {
        return new R<>(getUser());
    }


    /**
     * 修改登录用户密码
     */
    @ApiOperation("修改登录用户密码")
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        sysUserService.password(form);
        return R.ok();
    }

    /**
     * 用户信息
     */
    @ApiOperation("用户信息")
    @GetMapping("/info/{userId}")
    public R<SysUserVo> info(@PathVariable("userId") Long userId) {
        SysUserVo sysUserVo = sysUserService.info(userId);
        return new R<>(sysUserVo);
    }

    /**
     * 保存用户
     */
    @ApiOperation("保存用户")
    @SysLog("保存用户")
    @PostMapping("/save")
    public R save(@RequestBody SysUserSaveDto user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        sysUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @SysLog("修改用户")
    @PostMapping("/update")
    public R update(@RequestBody SysUserSaveDto user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        sysUserService.update(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @SysLog("删除用户")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] userIds) {
        sysUserService.del(userIds);
        return R.ok();
    }

    @ApiOperation("停用或启用")
    @SysLog("停用或启用")
    @PostMapping("/stop/{id}")
    public R stopOrStart(@PathVariable Long id) {
        sysUserService.stopOrStart(id);
        return R.ok();
    }

    @ApiOperation("根据角色查用户列表")
    @PostMapping("/listByRoleId")
    public R<List<SysUserVo>> listByRoleId(@RequestBody Long[] roleIdList) {
        List<SysUserVo> sysUserVos = sysUserService.listByRoleId(roleIdList);
        return new R<>(sysUserVos);
    }
}

package com.gkdz.server.modules.sys.controller;

import com.gkdz.server.common.annotation.SysLog;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.common.validator.ValidatorUtils;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.service.SysOrgService;
import com.gkdz.server.modules.sys.vo.SysOrgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gkdz.server.common.utils.ShiroUtils.getUserId;

@Api(tags = "组织机构接口")
@RestController
@RequestMapping("/sys/org")
public class SysOrgController {
    @Autowired
    private SysOrgService orgService;

    /**
     * 组织机构列表
     */
    @ApiOperation("查询全部组织机构列表(树形结构)")
    @GetMapping("/select")
    public R<List<SysOrgVo>> select() {
        List<SysOrgVo> list = orgService.selectAll();
        return new R<>(list);
    }

    /**
     * 组织机构信息
     */
    @ApiOperation("查询组织机构详情")
    @GetMapping("/info/{orgId}")
    public R<SysOrgEntity> info(@PathVariable("orgId") Long orgId) {
        SysOrgEntity org = orgService.info(orgId);
        return new R<>(org);
    }

    /**
     * 保存组织机构
     */
    @SysLog("保存组织机构")
    @ApiOperation("新建组织机构")
    @PostMapping("/save")
    public R save(@RequestBody SysOrgEntity org) {
        ValidatorUtils.validateEntity(org);
        org.setCreateUser(getUserId());
        orgService.save(org);
        return R.ok();
    }

    /**
     * 修改组织机构
     */
    @SysLog("修改组织机构")
    @ApiOperation("修改组织机构")
    @PostMapping("/update")
    public R update(@RequestBody SysOrgEntity org) {
        ValidatorUtils.validateEntity(org);
        org.setCreateUser(getUserId());
        orgService.update(org);
        return R.ok();
    }

    /**
     * 删除组织机构
     */
    @SysLog("删除组织机构")
    @ApiOperation("删除组织机构")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] orgIds) {
        List<Long> ids = orgService.deleteBatch(orgIds);
        if (CollectionUtils.isEmpty(ids)) {
            return R.ok();
        } else {
            return R.error("有用户/角色所属该组织或有下级组织,请先删除再执行此操作");
        }
    }
}

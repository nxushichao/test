package com.gkdz.server.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.gkdz.server.common.annotation.SysLog;
import com.gkdz.server.common.exception.RRException;
import com.gkdz.server.common.utils.Constant;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.modules.sys.entity.SysMenuEntity;
import com.gkdz.server.modules.sys.service.ShiroService;
import com.gkdz.server.modules.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统菜单接口
 *
 * @author Mark
 */
@Api(tags = "系统菜单接口")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @ApiOperation("导航菜单")
    @GetMapping("/nav")
    public R nav() {
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("menuList", menuList);
        map.put("permissions", permissions);
        return new R<>(map);
    }

    @ApiOperation("所有菜单列表")
    @GetMapping("/list")
    public R<List<SysMenuEntity>> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        HashMap<Long, SysMenuEntity> menuMap = new HashMap<>(12);
        for (SysMenuEntity s : menuList) {
            menuMap.put(s.getId(), s);
        }
        for (SysMenuEntity s : menuList) {
            SysMenuEntity parent = menuMap.get(s.getParentId());
            if (Objects.nonNull(parent)) {
                s.setParentName(parent.getName());
            }
        }

        return new R<>(menuList);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @ApiOperation("选择菜单(添加、修改菜单)")
    @GetMapping("/select")
    public R<List<SysMenuEntity>> select() {
        // 查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        // 添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return new R<>(menuList);
    }

    /**
     * 菜单信息
     */
    @ApiOperation("菜单信息")
    @GetMapping("/info/{menuId}")
    public R<SysMenuEntity> info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.getById(menuId);
        return new R<>(menu);
    }

    /**
     * 保存
     */
    @ApiOperation("保存菜单")
    @SysLog("保存菜单")
    @PostMapping("/save")
    public R save(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改菜单")
    @SysLog("修改菜单")
    @PostMapping("/update")
    public R update(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.ok();
    }

    @ApiOperation("删除菜单")
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    public R delete(@PathVariable("menuId") long menuId) {
        // 判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);
        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    @ApiOperation("验证参数是否正确")
    private void verifyForm(SysMenuEntity menu) {
        if (StrUtil.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }

        // 菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StrUtil.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }

        // 上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        // 目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() || menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        // 按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
        }
    }
}

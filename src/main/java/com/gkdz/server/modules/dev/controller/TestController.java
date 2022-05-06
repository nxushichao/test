package com.gkdz.server.modules.dev.controller;

import com.gkdz.server.modules.dev.entity.Test;
import com.gkdz.server.modules.dev.service.TestService;
import com.gkdz.server.common.utils.R;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.gkdz.server.modules.dev.dto.TestPageDto;
import com.gkdz.server.modules.dev.dto.TestSaveDto;
import com.gkdz.server.modules.dev.vo.TestInfoVo;
import javax.annotation.Resource;
import com.gkdz.server.common.validator.ValidatorUtils;
import com.gkdz.server.common.annotation.SysLog;


/**
*  Controller
*
    * @author 14651935
    * @since 2022-05-05
*/
@Api(tags = "管理")
@Slf4j
@RequestMapping("/dev/test")
@RestController
public class TestController {
    @Resource
    private TestService testService;

    /**
    * 列表
    */
    @PostMapping("/page")
    @ApiOperation("列表")
    public R<PageInfo<Test>> page(@RequestBody TestPageDto testPageDto){
        PageInfo<Test> page = testService.page(testPageDto);
        return new R<>(page);
    }


    /**
    * 查看
    */
    @GetMapping("/info/{id}")
    @ApiOperation("查看")
    public R<TestInfoVo> info(@PathVariable Long id){
        TestInfoVo testInfoVo = testService.info(id);
        return new R<>(testInfoVo);
    }

    /**
    * 保存
    */
    @PostMapping("/save")
    @ApiOperation("保存")
    @SysLog("新增")
    public R save(@RequestBody TestSaveDto testSaveDto){
        ValidatorUtils.validateEntity(testSaveDto);
        testService.add(testSaveDto);
        return R.ok();
    }

    /**
    * 修改
    */
    @PostMapping("/update")
    @ApiOperation("修改")
    @SysLog("修改")
    public R update(@RequestBody TestSaveDto testSaveDto){
        ValidatorUtils.validateEntity(testSaveDto);
        testService.edit(testSaveDto);
        return R.ok();
    }

    /**
    * 删除
    */
    @PostMapping("/delete")
    @ApiOperation("删除")
    @SysLog("删除")
    public R delete(@RequestBody Long[] ids){
        testService.delete(ids);
        return R.ok();
    }

}


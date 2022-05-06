package com.gkdz.server.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.common.enums.HttpResponseMessage;
import com.gkdz.server.modules.sys.entity.BasePage;
import com.gkdz.server.modules.sys.entity.SysDicType;
import com.gkdz.server.modules.sys.service.SysDicTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/19 15:03
 */
@RestController
@Slf4j
@Api(tags = "字典类型接口")
@RequestMapping("/dev/dic/type")
public class SysDicTypeController {
    @Autowired
    private SysDicTypeService dicTypeService;


    @PostMapping("/pageList")
    @ApiOperation("分页查询")
    public R<PageInfo<SysDicType>> pageList(@RequestBody BasePage basePage) {
        PageInfo<SysDicType> pageInfo = dicTypeService.pageList(basePage);
        return new R<>(pageInfo);
    }


    @PostMapping("/add")
    @ApiOperation("新增")
    public R<SysDicType> add(@RequestBody SysDicType dicType) {
        if (Objects.nonNull(dicType)) {
            dicTypeService.save(dicType);
            return new R<>(dicType);
        } else {
            return R.error(HttpResponseMessage.Param_Is_Null);
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public R update(@RequestBody SysDicType dicType) {
        dicTypeService.updateById(dicType);
        return R.ok();
    }


    /**
     * 删除暂时不提供，关联了dic_value表，不能单删
     *
     */
}

package com.gkdz.server.modules.sys.controller;

import com.gkdz.server.common.exception.RRException;
import com.gkdz.server.common.utils.Constant;
import com.gkdz.server.common.utils.ParamCheckCommonUtils;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.common.enums.HttpResponseMessage;
import com.gkdz.server.modules.sys.entity.SysDicValue;
import com.gkdz.server.modules.sys.service.SysDicValueService;
import com.gkdz.server.modules.sys.utils.SysDicValueParamCheck;
import com.gkdz.server.modules.sys.vo.SysDicValueTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sh
 * @version 1.0
 * @date 2022/1/19 15:03
 */
@RestController
@Api(tags = "字典值接口")
@Slf4j
@RequestMapping("/dev/dic/value")
public class SysDicValueController {
    @Autowired
    private SysDicValueService dicValueService;

    @GetMapping("/getByType/{type}")
    @ApiOperation("根据字典类型获取树")
    public R<List<SysDicValueTreeVo>> getByType(@PathVariable("type") String type) {
        try {
            Long aLong = ParamCheckCommonUtils.checkOneParamIsNumberType(type);
            List<SysDicValueTreeVo> list = dicValueService.getByType(aLong);
            return new R<>(list);
        } catch (RRException e) {
            return R.error(HttpResponseMessage.Param_Is_Error);
        }
    }


    @GetMapping("/getById/{id}")
    @ApiOperation("根据id获取该字典值详情")
    public R<SysDicValueTreeVo> getById(@PathVariable("id") Long id) {
        SysDicValueTreeVo tree = dicValueService.myGetById(id);
        return new R<>(tree);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public R add(@RequestBody SysDicValue dicValue) {
        String s = SysDicValueParamCheck.checkAdd(dicValue);
        if (Constant.SUCCESS.equals(s)) {
            dicValueService.save(dicValue);
            return R.ok();
        } else {
            return R.error(HttpResponseMessage.Param_Is_Error.getKey(), s);
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public R update(@RequestBody SysDicValue dicValue) {
        String s = SysDicValueParamCheck.checkUpdate(dicValue);
        if (Constant.SUCCESS.equals(s)) {
            dicValueService.updateById(dicValue);
            return R.ok();
        } else {
            return R.error(HttpResponseMessage.Param_Is_Error.getKey(), s);
        }
    }
}

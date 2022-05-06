package com.gkdz.server.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.utils.R;
import com.gkdz.server.modules.sys.dto.SysLogListDto;
import com.gkdz.server.modules.sys.entity.SysLogEntity;
import com.gkdz.server.modules.sys.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统日志
 *
 * @author Mark
 */
@Controller
@RequestMapping("/sys/log")
@Api(tags = "系统日志接口")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
    @PostMapping("/list")
    @ApiOperation("列表")
    public R<PageInfo<SysLogEntity>> list(@RequestBody SysLogListDto sysLogListDto) {
        PageInfo<SysLogEntity> page = sysLogService.queryPage(sysLogListDto);
        return new R<>(page);
    }
}

package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.modules.sys.dto.SysLogListDto;
import com.gkdz.server.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 *
 * @author Mark
 */
public interface SysLogService extends IService<SysLogEntity> {

    /**
     * 分页查询
     *
     * @param sysLogListDto 分页参数
     * @return 分页信息
     */
    PageInfo<SysLogEntity> queryPage(SysLogListDto sysLogListDto);
}

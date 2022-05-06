package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.utils.MyPageHelper;
import com.gkdz.server.modules.sys.dao.SysLogDao;
import com.gkdz.server.modules.sys.dto.SysLogListDto;
import com.gkdz.server.modules.sys.entity.SysLogEntity;
import com.gkdz.server.modules.sys.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mark
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageInfo<SysLogEntity> queryPage(SysLogListDto sysLogListDto) {
        String username = sysLogListDto.getUsername();
        MyPageHelper.startPage(sysLogListDto.getBasePage());
        List<SysLogEntity> list = this.lambdaQuery().like(StrUtil.isNotBlank(username), SysLogEntity::getUsername, username).list();

        return new PageInfo<>(list);
    }
}

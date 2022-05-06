package com.gkdz.server.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.utils.MyPageHelper;
import com.gkdz.server.modules.sys.dao.SysDicTypeMapper;
import com.gkdz.server.modules.sys.entity.BasePage;
import com.gkdz.server.modules.sys.entity.SysDicType;
import com.gkdz.server.modules.sys.service.SysDicTypeService;
import com.gkdz.server.modules.sys.service.SysDicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fengling
 * @since 2022-01-19
 */
@Service
public class SysDicTypeServiceImpl extends ServiceImpl<SysDicTypeMapper, SysDicType> implements SysDicTypeService {
    @Autowired
    private SysDicValueService dicValueService;


    @Override
    public PageInfo<SysDicType> pageList(BasePage basePage) {
        MyPageHelper.startPage(basePage);
        List<SysDicType> list = this.list();
        return new PageInfo<>(list);
    }


}

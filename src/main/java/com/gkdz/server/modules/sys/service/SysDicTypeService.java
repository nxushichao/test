package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.modules.sys.entity.BasePage;
import com.gkdz.server.modules.sys.entity.SysDicType;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-01-19
 */
public interface SysDicTypeService extends IService<SysDicType> {
    /**
     * 分页
     *
     * @param basePage 分页参数
     * @return 分页信息
     */
    PageInfo<SysDicType> pageList(BasePage basePage);
}

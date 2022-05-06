package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysDicValue;
import com.gkdz.server.modules.sys.vo.SysDicValueTreeVo;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author fengling
 * @since 2022-01-19
 */
public interface SysDicValueService extends IService<SysDicValue> {
    /**
     * 根据dic_type获取字典值数型结构
     *
     * @param aLong
     * @return
     */
    List<SysDicValueTreeVo> getByType(Long aLong);


    SysDicValueTreeVo myGetById(Long id);
}

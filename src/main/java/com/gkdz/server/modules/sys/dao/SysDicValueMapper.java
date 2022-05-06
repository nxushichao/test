package com.gkdz.server.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gkdz.server.modules.sys.entity.SysDicValue;
import com.gkdz.server.modules.sys.vo.SysDicValueTreeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典 Mapper 接口
 * </p>
 *
 * @author fengling
 * @since 2022-01-19
 */
@Mapper
public interface SysDicValueMapper extends BaseMapper<SysDicValue> {

    /**
     * 根据上级ID查找字典列表
     *
     * @param id 字典ID
     * @return 字典列表
     */
    List<SysDicValueTreeVo> queryListByParentId(@Param("id") Long id);
}

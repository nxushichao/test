package com.gkdz.server.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gkdz.server.modules.sys.entity.SysOrgUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 组织机构
 *
 * @author shen
 */
@Mapper
public interface SysOrgUserDao extends BaseMapper<SysOrgUserEntity> {
}

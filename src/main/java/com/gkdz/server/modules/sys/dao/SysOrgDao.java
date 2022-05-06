package com.gkdz.server.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构
 *
 * @author shen
 */
@Mapper
public interface SysOrgDao extends BaseMapper<SysOrgEntity> {
    /**
     * 根据组织机构id查询旗下所有角色
     *
     * @param orgId 组织ID
     * @return 组织实体
     */
    List<SysRoleEntity> queryRoleListByOrgId(@Param("orgId") Long orgId);
}

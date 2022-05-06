

package com.gkdz.server.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gkdz.server.modules.sys.entity.SysOrgRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组织机构
 *
 * @author shen
 */
@Mapper
public interface SysOrgRoleDao extends BaseMapper<SysOrgRoleEntity> {

    /**
     * 根据组织机构id获取旗下的所有角色id列表
     *
     * @param orgId 组织ID
     * @return 所有角色id列表
     */
    List<Long> queryRoleIdList(Long orgId);

    /**
     * 根据角色id集合删除记录
     *
     * @param roleIds
     * @return
     */
    int deleteBatch(Long[] roleIds);
}

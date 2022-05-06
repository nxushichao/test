package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import com.gkdz.server.modules.sys.vo.SysOrgVo;

import java.util.List;

/**
 * 组织机构
 */
public interface SysOrgService extends IService<SysOrgEntity> {

    /**
     * 查询角色列表
     *
     * @param orgId 组织ID
     * @return 角色集合
     */
    List<SysRoleEntity> queryRoleList(Long orgId);

    /**
     * 组织更新
     *
     * @param org 组织
     */
    void update(SysOrgEntity org);

    /**
     * 批量删除
     *
     * @param orgIds 组织ID集合
     * @return 删除的列表
     */
    List<Long> deleteBatch(Long[] orgIds);

    /**
     * 根据参数查询组织机构树形结构
     *
     * @return 组织树
     */
    List<SysOrgVo> selectAll();


    SysOrgEntity info(Long id);
}

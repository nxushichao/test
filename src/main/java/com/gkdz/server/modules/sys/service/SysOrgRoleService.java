

package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysOrgRoleEntity;


/**
 * 组织机构<==>角色
 */
public interface SysOrgRoleService extends IService<SysOrgRoleEntity> {

    void saveOrUpdate(Long roleId, Long orgId);

    int deleteBatch(Long[] roleIds);
}

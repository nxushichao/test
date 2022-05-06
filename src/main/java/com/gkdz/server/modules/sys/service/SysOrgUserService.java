package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysOrgUserEntity;

import java.util.List;

/**
 * 组织机构<==>用户
 *
 * @author shen
 */
public interface SysOrgUserService extends IService<SysOrgUserEntity> {
    List<SysOrgEntity> queryOrgByUserId(Long userId);

    void saveOrUpdate(Long userId, List<SysOrgEntity> list);

    /**
     * 保存用户和组织的关系
     *
     * @param userId    用户ID
     * @param orgIdList 组织ID列表
     */
    void saveOrUpdateOrgList(Long userId, List<Long> orgIdList);
}

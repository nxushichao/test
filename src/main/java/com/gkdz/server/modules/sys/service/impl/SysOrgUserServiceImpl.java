

package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.modules.sys.dao.SysOrgUserDao;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysOrgUserEntity;
import com.gkdz.server.modules.sys.service.SysOrgService;
import com.gkdz.server.modules.sys.service.SysOrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 组织机构
 *
 * @author shen
 */
@Service("sysOrgUserService")
public class SysOrgUserServiceImpl extends ServiceImpl<SysOrgUserDao, SysOrgUserEntity> implements SysOrgUserService {
    @Autowired
    private SysOrgService sysOrgService;

    @Override
    public List<SysOrgEntity> queryOrgByUserId(Long userId) {
        List<SysOrgUserEntity> sysOrgUserEntitys = this.list(new QueryWrapper<SysOrgUserEntity>().lambda().eq(SysOrgUserEntity::getUserId, userId));
        if (!CollectionUtils.isEmpty(sysOrgUserEntitys)) {
            List<SysOrgEntity> sysOrgEntities = sysOrgService.list(new QueryWrapper<SysOrgEntity>().lambda().in(SysOrgEntity::getId, sysOrgUserEntitys.stream().map(SysOrgUserEntity::getOrgId).collect(Collectors.toList())));
            return sysOrgEntities;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(Long userId, List<SysOrgEntity> list) {
        this.remove(new QueryWrapper<SysOrgUserEntity>().lambda().eq(SysOrgUserEntity::getUserId, userId));
        if (!CollectionUtils.isEmpty(list)) {
            List<SysOrgUserEntity> orgUserEntityList = new LinkedList<>();
            for (SysOrgEntity sysOrgEntity : list) {
                SysOrgUserEntity sysOrgUserEntity = new SysOrgUserEntity();
                sysOrgUserEntity.setUserId(userId);
                sysOrgUserEntity.setOrgId(sysOrgEntity.getId());
                orgUserEntityList.add(sysOrgUserEntity);
            }
            if (!CollectionUtils.isEmpty(orgUserEntityList)) {
                this.saveBatch(orgUserEntityList);
            }

        }
    }

    @Override
    public void saveOrUpdateOrgList(Long userId, List<Long> orgIdList) {

        //先删除用户与角色关系
        HashMap<String, Object> map = MapUtil.newHashMap();
        map.put("user_id", userId);
        this.removeByMap(map);
        if (orgIdList == null || orgIdList.size() == 0) {
            return;
        }

        //保存用户与角色关系
        for (Long orgId : orgIdList) {
            SysOrgUserEntity sysOrgUserEntity = new SysOrgUserEntity();
            sysOrgUserEntity.setUserId(userId);
            sysOrgUserEntity.setOrgId(orgId);
            this.save(sysOrgUserEntity);
        }
    }
}
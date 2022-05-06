package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.modules.sys.dao.SysOrgDao;
import com.gkdz.server.modules.sys.entity.SysOrgEntity;
import com.gkdz.server.modules.sys.entity.SysOrgRoleEntity;
import com.gkdz.server.modules.sys.entity.SysOrgUserEntity;
import com.gkdz.server.modules.sys.entity.SysRoleEntity;
import com.gkdz.server.modules.sys.service.SysOrgRoleService;
import com.gkdz.server.modules.sys.service.SysOrgService;
import com.gkdz.server.modules.sys.service.SysOrgUserService;
import com.gkdz.server.modules.sys.vo.SysOrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 组织机构
 */
@Service("sysOrgService")
public class SysOrgServiceImpl extends ServiceImpl<SysOrgDao, SysOrgEntity> implements SysOrgService {
    @Autowired
    private SysOrgUserService sysOrgUserService;
    @Autowired
    private SysOrgRoleService sysOrgRoleService;


    @Override
    public List<SysRoleEntity> queryRoleList(Long orgId) {
        return baseMapper.queryRoleListByOrgId(orgId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(SysOrgEntity org) {
        this.updateById(org);
    }

    /**
     * 如果还有用户或者角色属于该组织，该组织不允许被删除 (需先删除所属用户与角色)
     *
     * @param orgIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Long> deleteBatch(Long[] orgIds) {
        List<Long> ids = new LinkedList<>();
        List<Long> rollbackIds = new LinkedList<>();
        for (Long orgId : orgIds) {
            List<SysOrgRoleEntity> orgRoleEntityList = sysOrgRoleService.list(new QueryWrapper<SysOrgRoleEntity>().eq("org_id", orgId));
            List<SysOrgUserEntity> orgUserEntityList = sysOrgUserService.list(new QueryWrapper<SysOrgUserEntity>().eq("org_id", orgId));
            List<SysOrgEntity> list = this.lambdaQuery().eq(SysOrgEntity::getParentOrgId, orgId).list();
            if (CollectionUtils.isEmpty(orgRoleEntityList) && CollectionUtils.isEmpty(orgUserEntityList) && CollectionUtils.isEmpty(list)) {
                ids.add(orgId);
                baseMapper.deleteBatchIds(ids);
            } else {
                rollbackIds.add(orgId);
            }
        }
        return rollbackIds;
    }

    @Override
    public List<SysOrgVo> selectAll() {
        List<SysOrgEntity> list = this.list();
        List<SysOrgVo> orgList = BeanUtil.copyToList(list, SysOrgVo.class);
        // 进行树形处理
        List<SysOrgEntity> baseList = this.list(new QueryWrapper<SysOrgEntity>().lambda().eq(SysOrgEntity::getParentOrgId, 0));
        List<SysOrgVo> sysOrgVos = BeanUtil.copyToList(baseList, SysOrgVo.class);

        for (SysOrgVo sysOrgVo : sysOrgVos) {
            List<SysOrgVo> childList = treeChange(sysOrgVo.getId(), orgList);
            sysOrgVo.setList(childList);
        }
        return sysOrgVos;
    }


    private List<SysOrgVo> treeChange(Long orgId, List<SysOrgVo> list) {
        List<SysOrgVo> returnList = new LinkedList<>();
        for (SysOrgVo sysOrgEntity : list) {
            if (orgId.equals(sysOrgEntity.getParentOrgId())) {
                List<SysOrgVo> childList = treeChange(sysOrgEntity.getId(), list);
                sysOrgEntity.setList(childList);
                returnList.add(sysOrgEntity);
            }
        }
        return returnList;
    }

    @Override
    public SysOrgEntity info(Long id) {
        return this.getById(id);
    }
}

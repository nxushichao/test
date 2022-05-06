package com.gkdz.server.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.common.exception.RRException;
import com.gkdz.server.common.utils.MyPageHelper;
import com.gkdz.server.common.utils.ShiroUtils;
import com.gkdz.server.common.validator.Assert;
import com.gkdz.server.modules.sys.dao.SysUserDao;
import com.gkdz.server.modules.sys.dto.SysUserListDto;
import com.gkdz.server.modules.sys.dto.SysUserSaveDto;
import com.gkdz.server.modules.sys.entity.*;
import com.gkdz.server.modules.sys.form.PasswordForm;
import com.gkdz.server.modules.sys.service.SysOrgService;
import com.gkdz.server.modules.sys.service.SysOrgUserService;
import com.gkdz.server.modules.sys.service.SysUserRoleService;
import com.gkdz.server.modules.sys.service.SysUserService;
import com.gkdz.server.modules.sys.vo.SysOrgInfoVo;
import com.gkdz.server.modules.sys.vo.SysUserVo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.gkdz.server.common.utils.ShiroUtils.getUserId;

/**
 * 系统用户
 *
 * @author Mark
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private SysOrgUserService sysOrgUserService;

    @Override
    public void del(Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            throw new RRException("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            throw new RRException("当前用户不能删除");
        }
        this.deleteBatch(userIds);
    }

    @Override
    public void password(PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");
        // sha256加密
        String password = new Sha256Hash(form.getPassword(), ShiroUtils.getUserEntity().getSalt()).toHex();
        // sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), ShiroUtils.getUserEntity().getSalt()).toHex();

        // 更新密码
        boolean flag = this.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            throw new RRException("原密码不正确");
        }
    }

    @Override
    public PageInfo<SysUserVo> queryPage(SysUserListDto sysUserListDto) {
        //查询所有不包含这些角色的 用户ID
        Set<Long> longSet = new HashSet<>();
        if (sysUserListDto.getRoleId() == null) {
            //默认不查角色5,6
            ArrayList<Long> longs = new ArrayList<>();
            longs.add(5L);
            longs.add(6L);
            List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().notIn(SysUserRoleEntity::getRoleId, longs).list();
            longSet = list.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toSet());
        } else {
            List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, sysUserListDto.getRoleId()).list();
            longSet = list.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toSet());
        }
        MyPageHelper.startPage(sysUserListDto.getBasePage());
        List<SysUserVo> list = this.baseMapper.queryByRoleId(sysUserListDto.getRoleId(), sysUserListDto.getOrgId(), sysUserListDto.getUsername(), sysUserListDto.getStatus(), longSet);
        PageInfo<SysUserVo> pageInfo = new PageInfo<>(list);
        List<SysUserVo> records = pageInfo.getList();
        if (!CollectionUtils.isEmpty(records)) {
            for (SysUserVo record : records) {
                this.getExtraInformation(record);
            }
        }
        return pageInfo;
    }

    @Override
    public SysUserVo info(Long userId) {
        SysUserEntity user = this.getById(userId);
        if (user == null) {
            throw new RRException("未找到对应用户");
        }
        SysUserVo sysUserVo = BeanUtil.copyProperties(user, SysUserVo.class);
        this.getExtraInformation(sysUserVo);
        return sysUserVo;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveUser(SysUserSaveDto user) {
        SysUserEntity one = this.lambdaQuery().eq(SysUserEntity::getUsername, user.getUsername()).one();
        if (one != null) {
            throw new RRException("该用户已存在");
        }
        String password = user.getPassword();
        if (StrUtil.isBlank(password)) {
            password = "123456";
        }
        user.setCreateTime(new Date());
        // sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(password, salt).toHex());
        user.setSalt(salt);
        SysUserEntity sysUserEntity = BeanUtil.copyProperties(user, SysUserEntity.class);
        this.saveOrUpdate(sysUserEntity);

        // 保存用户与角色关系
        sysUserRoleService.saveOrUpdate(sysUserEntity.getId(), user.getRoleIdList());

        // 保存用户与组织的关系
        sysOrgUserService.saveOrUpdateOrgList(sysUserEntity.getId(), user.getOrgIdList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(SysUserSaveDto user) {
        SysUserEntity one = this.lambdaQuery().eq(SysUserEntity::getUsername, user.getUsername()).ne(SysUserEntity::getId, user.getId()).one();
        if (one != null) {
            throw new RRException("该用户已存在");
        }
        user.setPassword(null);

        SysUserEntity sysUserEntity = BeanUtil.copyProperties(user, SysUserEntity.class);
        this.updateById(sysUserEntity);

        // 保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
        // 保存用户与组织的关系
        sysOrgUserService.saveOrUpdateOrgList(user.getId(), user.getOrgIdList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBatch(Long[] userIdList) {
        this.removeByIds(Arrays.asList(userIdList));
        sysOrgUserService.remove(new QueryWrapper<SysOrgUserEntity>().lambda().in(SysOrgUserEntity::getUserId, userIdList));
        sysUserRoleService.remove(new QueryWrapper<SysUserRoleEntity>().lambda().in(SysUserRoleEntity::getUserId, userIdList));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity, new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getId, userId).eq(SysUserEntity::getPassword, password));
    }

    @Override
    public void stopOrStart(Long id) {
        SysUserEntity user = this.getById(id);
        if (user.getStatus() == 0) {
            user.setStatus(1);
        } else {
            user.setStatus(0);
        }
        this.updateById(user);
    }

    @Override
    public List<SysUserVo> listByRoleId(Long[] roleId) {
        Set<Long> longSet;
        List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().in(SysUserRoleEntity::getRoleId, roleId).list();
        longSet = list.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toSet());
        List<SysUserEntity> userEntityList = this.lambdaQuery().in(SysUserEntity::getId, longSet).list();
        return BeanUtil.copyToList(userEntityList, SysUserVo.class);
    }

    @Override
    public void reset(Long userId) {
        Long loginUserId = getUserId();
        Long count = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, loginUserId).eq(SysUserRoleEntity::getRoleId, 1L).count();
        if (count > 0) {
            //重置密码
            SysUserEntity sysUserEntity = new SysUserEntity();
            SysUserEntity thisUser = this.getById(userId);
            if (Objects.nonNull(thisUser)) {
                sysUserEntity.setPassword("123456");
                sysUserEntity.setSalt(null);
            } else {
                throw new RRException("该用户不存在!");
            }
        } else {
            throw new RRException("请使用管理员账号更改");
        }
    }


    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }


    /**
     * 获取额外的用户信息
     *
     * @param sysUserVo 用户
     */
    private void getExtraInformation(SysUserVo sysUserVo) {
        List<SysOrgEntity> sysOrgEntity = sysOrgUserService.queryOrgByUserId(sysUserVo.getId());
        List<SysRoleEntity> sysRoleEntityList = sysUserRoleService.queryRoleByUserId(sysUserVo.getId());
        sysUserVo.setRoleEntityList(sysRoleEntityList);
        List<SysOrgInfoVo> sysOrgInfoVos = BeanUtil.copyToList(sysOrgEntity, SysOrgInfoVo.class);
        if (sysOrgInfoVos != null && sysOrgInfoVos.size() > 0) {
            sysOrgInfoVos.forEach(sysOrgInfoVo -> {
                if (sysOrgInfoVo.getOrgLevel() == 3) {
                    SysOrgEntity parentOrg = sysOrgService.getById(sysOrgInfoVo.getParentOrgId());
                    sysOrgInfoVo.setParentOrgName(parentOrg.getOrgName());
                }
            });
        }
        sysUserVo.setOrgEntityList(sysOrgInfoVos);
        Long leader = sysUserVo.getLeader();
        if (leader != null) {
            SysUserEntity leaderObj = this.getById(leader);
            sysUserVo.setLeaderName(leaderObj.getName());
        }
    }

}

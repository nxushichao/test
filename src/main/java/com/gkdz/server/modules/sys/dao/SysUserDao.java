package com.gkdz.server.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gkdz.server.modules.sys.entity.SysUserEntity;
import com.gkdz.server.modules.sys.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 系统用户
 *
 * @author Mark
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId 用户ID
     * @return 菜单ID列表
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 根据角色id，获取对象权限列表
     *
     * @param roleIds
     * @return
     */
    List<String> queryPermsByRoleIds(Long[] roleIds);


    List<SysUserVo> queryByRoleId(Long roleId, Long orgId, String username, Integer status, Set<Long> userIdList);

    List<SysUserVo> listByRoleId(Long[] roleIdList);

}

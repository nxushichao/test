package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.gkdz.server.modules.sys.dto.SysUserListDto;
import com.gkdz.server.modules.sys.dto.SysUserSaveDto;
import com.gkdz.server.modules.sys.entity.SysUserEntity;
import com.gkdz.server.modules.sys.form.PasswordForm;
import com.gkdz.server.modules.sys.vo.SysUserVo;

import java.util.List;

/**
 * 系统用户
 *
 * @author Mark
 */
public interface SysUserService extends IService<SysUserEntity> {

    void del(Long[] userIds);

    void password(PasswordForm form);

    /**
     * 分页查询用户
     *
     * @param sysUserListDto 分页参数
     * @return 分页信息
     */
    PageInfo<SysUserVo> queryPage(SysUserListDto sysUserListDto);

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserVo info(Long userId);

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
     * @param username 角色名
     * @return 系统用户
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 保存用户
     *
     * @param user 用户信息
     */
    void saveUser(SysUserSaveDto user);

    /**
     * 修改用户
     *
     * @param user 用户信息
     */
    void update(SysUserSaveDto user);

    /**
     * 删除用户
     *
     * @param userIds 用户ID数组
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    /**
     * 停用启用
     *
     * @param id 用户ID
     */
    void stopOrStart(Long id);

    /**
     * 根据角色列表查找用户列表
     *
     * @param roleIdList 角色列表
     * @return 用户列表
     */
    List<SysUserVo> listByRoleId(Long[] roleIdList);

    /**
     * 重置密码
     *
     * @param userId 用户ID
     */
    void reset(Long userId);
}

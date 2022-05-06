

package com.gkdz.server.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gkdz.server.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author Mark
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    /**
     * 根据token查找用户token实体
     *
     * @param token token
     * @return 用户token实体
     */
    SysUserTokenEntity queryByToken(String token);

}

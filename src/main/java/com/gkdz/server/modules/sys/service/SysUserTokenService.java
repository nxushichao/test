

package com.gkdz.server.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gkdz.server.modules.sys.entity.SysUserTokenEntity;

import java.util.Map;

/**
 * 用户Token
 *
 * @author Mark
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

    /**
     * 生成token *
     *
     * @param userId 用户ID
     * @return json
     */
    Map<String, Object> createToken(long userId);

    /**
     * 退出，修改token值
     *
     * @param userId 用户ID
     */
    void logout(long userId);

}

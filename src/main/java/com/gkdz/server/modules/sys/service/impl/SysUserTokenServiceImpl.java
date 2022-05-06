package com.gkdz.server.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gkdz.server.modules.sys.dao.SysUserTokenDao;
import com.gkdz.server.modules.sys.entity.SysUserTokenEntity;
import com.gkdz.server.modules.sys.oauth2.TokenGenerator;
import com.gkdz.server.modules.sys.service.SysUserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    // 12小时后过期
    private static final int EXPIRE = 3600 * 12;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> createToken(long userId) {

        // 生成一个token
        String token = TokenGenerator.generateValue();

        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        // 判断是否生成过token
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 更新token
            this.updateById(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>(12);
        map.put("token", token);
        map.put("expire", EXPIRE);
        map.put("userId", userId);
        return map;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void logout(long userId) {
        // 生成一个token
        String token = TokenGenerator.generateValue();

        // 修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}

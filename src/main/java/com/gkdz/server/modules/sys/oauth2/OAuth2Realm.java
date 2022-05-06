package com.gkdz.server.modules.sys.oauth2;

import cn.hutool.core.date.DateUtil;
import com.gkdz.server.modules.sys.entity.SysUserEntity;
import com.gkdz.server.modules.sys.entity.SysUserTokenEntity;
import com.gkdz.server.modules.sys.service.ShiroService;
import com.gkdz.server.modules.sys.service.SysUserTokenService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * 认证
 *
 * @author wu
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
  @Autowired
  private ShiroService shiroService;
  @Resource
  private SysUserTokenService sysUserTokenService;
  @Value("${spring.profiles.active}")
  private String profiles;
  @Value("${knife4j.token}")
  private String defaultToken;

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof OAuth2Token;
  }

  /**
   * 授权(验证权限时调用)
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
    Long userId = user.getId();

    // 用户权限列表
    Set<String> permsSet = shiroService.getUserPermissions(userId);

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.setStringPermissions(permsSet);
    return info;
  }

  /**
   * 认证(登录时调用)
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String accessToken = (String) token.getPrincipal();
    SysUserTokenEntity tokenEntity = null;

    //开发环境下 token为配置文件默认token时 默认管理员
    if ("dev".equals(profiles) && accessToken.equals(defaultToken)) {
      tokenEntity = sysUserTokenService.getById(1);
      tokenEntity.setExpireTime(new Date(DateUtil.date().getTime() + 3600 * 12 * 1000));
    }
    // 根据accessToken，查询用户信息
    if (tokenEntity == null) {
      tokenEntity = shiroService.queryByToken(accessToken);
    }
    // token失效
    if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
      throw new IncorrectCredentialsException("token失效，请重新登录");
    }

    //加入文档开发token
    //判断是否为开发环境
    // 查询用户信息
    SysUserEntity user = shiroService.queryUser(tokenEntity.getId());
    // 账号锁定
    if (user.getStatus() == 0) {
      throw new LockedAccountException("账号已被锁定,请联系管理员");
    }


    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
    return info;
  }
}

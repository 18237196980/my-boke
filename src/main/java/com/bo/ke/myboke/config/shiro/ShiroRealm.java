package com.bo.ke.myboke.config.shiro;

import com.bo.ke.myboke.common.ShiroUser;
import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.service.UserService;
import com.bo.ke.myboke.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义的ShiroRealm，用于进行用户验证及授权
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 必须重写此方法，不然Shiro会报UnsupportedTokenException错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof WebToken;
    }

    /**
     * 授权，即向通过认证的用户赋予指定的权限
     *
     * @param principals 身份信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============Shiro权限授权开始====");

        return null;
    }


    /**
     * 认证，即用户登录时进行的认证动作 doLogin
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken auth) throws AuthenticationException {
        log.info("===============权限认证开始====");
        WebToken jwt = (WebToken) auth;
        if (jwt.getCredentials() == null) {
            throw new AuthenticationException("token为空，请重新登录!");
        }
        String userId = JwtUtil.getIdFromToken(jwt.getCredentials() + "");
        User user = userService.get(userId);
        if (user == null) {
            throw new UnknownAccountException("未知用户");
        }
        if (user.getStatus() == 0) {
            throw new LockedAccountException("用户未激活");
        }
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(user, shiroUser);

        return new SimpleAuthenticationInfo(shiroUser, jwt.getCredentials(), getName());
    }

}

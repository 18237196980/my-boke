package com.bo.ke.myboke.config.shiro;

import com.bo.ke.myboke.common.Const;
import com.bo.ke.myboke.common.ShiroUser;
import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.exception.CusException;
import com.bo.ke.myboke.service.UserService;
import com.bo.ke.myboke.utils.JwtUtils;
import com.ex.framework.util.SpringUtils;
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

//    @Autowired
//    private UserService userService; 不能注入，会优先创建bean,导致UserService事务无法生效 https://cloud.tencent.com/developer/article/1553589

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
        String userId = JwtUtils.getElement(jwt.getCredentials()
                                               .toString(), Const.ID);

        UserService userService = SpringUtils.getBean(UserService.class);
        User user = userService.get(userId);
        if (user == null) {
            throw new UnknownAccountException("用户不存在,请重新登陆！");
        }
        if (user.getStatus() == 0) {
            throw new LockedAccountException("用户未激活,请重新登陆！");
        }
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(user, shiroUser);

        return new SimpleAuthenticationInfo(shiroUser, jwt.getCredentials(), getName());
    }

}

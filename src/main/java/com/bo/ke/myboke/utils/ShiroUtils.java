package com.bo.ke.myboke.utils;

import com.bo.ke.myboke.common.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * 从shiro中获取当前登录用户信息
 */
public class ShiroUtils {

    public static ShiroUser getUser() {
        try {
            ShiroUser user = (ShiroUser) SecurityUtils.getSubject()
                                                      .getPrincipal();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserId() {
        ShiroUser user = getUser();
        if (user == null) {
            return null;
        } else {
            return user.getId();
        }
    }

}

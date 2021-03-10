package com.bo.ke.myboke.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.ke.myboke.common.Record;
import com.bo.ke.myboke.common.Result;
import com.bo.ke.myboke.common.ShiroUser;
import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.service.UserService;
import com.bo.ke.myboke.utils.JwtUtil;
import com.bo.ke.myboke.utils.MD5Utils;
import com.bo.ke.myboke.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.Subject;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/boke/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登陆 返回jwt
     * @param user
     * @return
     */
    @RequestMapping("login")
    public Object login(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            return Result.error("请输入用户名");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return Result.error("请输入密码");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User model = userService.getOne(wrapper);
        if (model != null) {
            if (model.getStatus() == 0) {
                return Result.error("用户未激活");
            }
        } else {
            return Result.error("用户不存在");
        }
        String md5Pwd = MD5Utils.md5(user.getPassword());
        if (StringUtils.equals(md5Pwd, model.getPassword())) {
            // 登陆成功
            String jwt = JwtUtil.generateToken(model);
            return Result.success(Record.build()
                                        .set("id", model.getId())
                                        .set("jwt", jwt)
                                        .set("username", model.getUsername())
                                        .set("avatar", model.getAvatar()));
        }else {
            return Result.error("密码错误");
        }
    }

    @RequestMapping("getOne")
    public Object getOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User one = userService.getOne(queryWrapper.eq("id", "2018110615541619824983321"));

        return one;
    }

}

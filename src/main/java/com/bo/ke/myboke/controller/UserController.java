package com.bo.ke.myboke.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.service.UserService;
import com.ex.framework.data.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2021-03-09
 */
//@RestController
@RequestMapping("/boke/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
//
//    @Autowired
//    MBlogService mBlogService;

    /**
     * 登陆 返回jwt
     *
     * @return
     */

    @RequestMapping("login")
    public Object login() {

        //userService.testTrans();
        //mBlogService.addMblog();
        return "kk";
        /*if (StringUtils.isEmpty(user.getUsername())) {
            return Result.error("请输入用户名");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return Result.error("请输入密码");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User model = userService.get(wrapper);
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
            String jwt = JwtUtils.generateToken(model);
            return Result.success(Record.build()
                                        .set("id", model.getId())
                                        .set("jwt", jwt)
                                        .set("username", model.getUsername())
                                        .set("avatar", model.getAvatar()));
        } else {
            return Result.error("密码错误");
        }*/
    }

    @RequestMapping("getOne")
    public Result getOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User one = userService.get(queryWrapper.eq("id", "2018110615541619824983321"));

        return Result.success(one);
    }

}

package com.bo.ke.myboke.service;

import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.mapper.UserMapper;
import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService extends BaseCRUDService<UserMapper, User> {
    @Autowired
    MBlogService mBlogService;
    @Autowired
    UserMapper userMapper;

    @Transactional
    public void testTrans() {
        User user = new User();
        user.setId(IDUtils.getSequenceStr());
        user.setUsername("刘备-" + System.currentTimeMillis());

        userMapper.insertUser(user);


        // 手动制造异常
        mBlogService.addMblog();

    }

}

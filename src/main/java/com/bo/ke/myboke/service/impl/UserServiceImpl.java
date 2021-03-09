package com.bo.ke.myboke.service.impl;

import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.mapper.UserMapper;
import com.bo.ke.myboke.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

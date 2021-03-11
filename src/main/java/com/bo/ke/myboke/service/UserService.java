package com.bo.ke.myboke.service;

import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.mapper.UserMapper;
import com.ex.framework.base.BaseCRUDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService extends BaseCRUDService<UserMapper, User> {
}

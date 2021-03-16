package com.bo.ke.myboke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.ke.myboke.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-03-09
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    void insertUser(User user);
}

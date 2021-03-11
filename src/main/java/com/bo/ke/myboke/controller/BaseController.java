package com.bo.ke.myboke.controller;

import com.bo.ke.myboke.entity.User;
import com.ex.framework.data.Record;
import com.ex.framework.util.mapper.JsonMapper;
import com.ex.framework.web.ExPage;
import org.apache.shiro.SecurityUtils;

public abstract class BaseController {

    /**
     * 获取当前用户id
     *
     * @return
     */
    protected String getCurrentUserId() {
        User user = getCurrentShiroUser();
        if (user == null) {
            return "";
        }
        return user.getId();
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    protected User getCurrentShiroUser() {
        User user = (User) SecurityUtils.getSubject()
                                        .getPrincipal();
        return user;
    }


    protected String toJson(Object object) {
        return JsonMapper.defaultMapper()
                         .toJson(object);
    }

    protected ExPage parseExPage(Record record) {
        if (record == null) {
            return new ExPage(1, 10, "id", "asc");
        }

        int pageNumber = record.getInt("pageNo", 1);
        int pageSize = record.getInt("pageSize", 10);
        String sort = record.getString("sort", "id");
        String order = record.getString("order", "desc");

        return new ExPage(pageNumber, pageSize, sort, order);
    }

}

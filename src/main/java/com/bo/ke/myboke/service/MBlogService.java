package com.bo.ke.myboke.service;

import com.bo.ke.myboke.entity.MBlog;
import com.bo.ke.myboke.mapper.MBlogMapper;
import com.bo.ke.myboke.utils.ShiroUtils;
import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MBlogService extends BaseCRUDService<MBlogMapper, MBlog> {

    //    @Transactional
    public Result addEditBlog(MBlog mBlog) {
        if (StringUtils.isEmpty(mBlog.getTitle())) {
            return Result.error("请输入标题");
        }
        if (StringUtils.isEmpty(mBlog.getDescription())) {
            return Result.error("请输入描述");
        }
        if (StringUtils.isNotEmpty(mBlog.getId())) {
            updateById(mBlog);
        } else {
            mBlog.setId(IDUtils.getSequenceStr());
            mBlog.setUId(ShiroUtils.getUserId());
            mBlog.setCreateTime(LocalDateTime.now());
            mBlog.setStatus(1);
            add(mBlog);
        }
        return Result.success();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addMblog() {

        MBlog mBlog = new MBlog();
        mBlog.setId(IDUtils.getSequenceStr());
        mBlog.setTitle("清仓-" + System.currentTimeMillis());


        add(mBlog);

        int aa = 10 / 0;


    }
}

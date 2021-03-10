package com.bo.ke.myboke.controller;


import com.bo.ke.myboke.common.Result;
import com.bo.ke.myboke.service.MBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/boke/blogs")
public class MBlogController {

    @Autowired
    MBlogService mBlogService;

    public Result list(@RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {

        return Result.success();
    }

}

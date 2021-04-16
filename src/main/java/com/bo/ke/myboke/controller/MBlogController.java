package com.bo.ke.myboke.controller;


import com.bo.ke.myboke.config.websocket.WebSocketServer;
import com.bo.ke.myboke.entity.MBlog;
import com.bo.ke.myboke.entity.User;
import com.bo.ke.myboke.service.MBlogService;
import com.bo.ke.myboke.service.UserService;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.data.Result;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/boke/blog")
public class MBlogController extends BaseController {

    @Autowired
    MBlogService mBlogService;
    @Autowired
    WebSocketServer socketServer;
    @Autowired
    UserService userService;

    @PostMapping("list")
    public TableResult list(@RecordBody Record record) {
        ExPage page = parseExPage(record);
        ExPageResult<MBlog> pageResult = mBlogService.list(page, null);
        return TableResult.success(pageResult);
    }

    @PostMapping("addEditBlog")
    public Result addEditBlog(@RequestBody MBlog mBlog) {
        try {
            return mBlogService.addEditBlog(mBlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("操作失败");
    }

    @GetMapping("getBolgById")
    public Result getBolgById(String id) {
        try {
            List<User> users = userService.list();
            for (User user : users) {
                if (!user.getId()
                         .equals(getCurrentUserId())) {
                    socketServer.sendInfo("我的id是:" + user.getId(), user.getId());
                    break;
                }
            }
            return Result.success(mBlogService.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("博客不存在");
    }

}

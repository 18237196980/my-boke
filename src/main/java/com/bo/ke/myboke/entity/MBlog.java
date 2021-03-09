package com.bo.ke.myboke.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-03-09
 */
@TableName("m_blog")
@Data
public class MBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String uId;

    private String title;

    private String description;

    private String content;

    private LocalDateTime createTime;

    private Integer status;

}

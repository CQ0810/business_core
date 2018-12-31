package com.tio.app.contents.entities;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Mybatis Generator 2018/11/14
 */
@Data
public class Posts extends Model<Posts> {
    /* */
    private Integer id;

    /* */
    private String title;

    /* */
    private String content;

    /* */
    private Integer createdAt;

    /* */
    private Integer updatedAt;

    /* */
    private Integer userId;

    /* */
    private String discr;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
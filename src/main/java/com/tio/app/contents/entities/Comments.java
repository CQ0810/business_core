package com.tio.app.contents.entities;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Mybatis Generator 2018/11/14
 */
@Data
@SuppressWarnings("serial")
public class Comments extends Model<Comments> {
    /* */
    private Integer id;

    /* */
    private String contents;

    /* */
    private Integer createdAt;

    /* */
    private Integer updatedAt;

    /* */
    private Integer userId;

    /* */
    private Integer postsId;

    /* */
    private String discr;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
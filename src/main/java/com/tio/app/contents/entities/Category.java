package com.tio.app.contents.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by Mybatis Generator 2018/11/14
 */
@Data
public class Category {
    /* */
    private Integer id;

    /* */
    @NotNull
    @Length(min = 5, max = 16)
    /**/
    private String name;

    /* */
    @NotNull
    /**/
    private Integer parentId;

    /* */
    @NotNull
    /**/
    private Integer sort;

    /* */
    private Integer isDisable;

    /* */
    private Integer isDelete;

    /* */
    private Integer thumbImagesId;

    /* */
    private Integer sDataRank;
}
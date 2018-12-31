package com.tio.app.common.vo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PageVO {

    @TableField(exist = false)
    private Integer page;

    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private Integer size;

    @TableField(exist = false)
    private String statTime;

    @TableField(exist = false)
    private String endTime;
}
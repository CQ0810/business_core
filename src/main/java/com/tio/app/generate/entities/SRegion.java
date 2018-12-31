package com.tio.app.generate.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class SRegion {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 层级
     */
    private Integer type;

    /**
     * 名字
     */
    private String name;

    /**
     * 下级城市
     */
    @TableField(exist = false)
    private List<SRegion> children;
}

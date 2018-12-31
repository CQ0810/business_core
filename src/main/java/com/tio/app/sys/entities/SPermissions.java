package com.tio.app.sys.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SPermissions {
    /**
     * 主键ID
     */
    @TableId
    private Integer id;
    /**
     * 权限码
     */
    private String name;
    /**
     * 权限保护类型(web,php，主要用于PHP)
     */
    private String guardName;
    /**
     * 权限名称
     */
    private String showName;
    /**
     * 请求的url, 可以填正则表达式
     */
    private String url;
    /**
     * 类型，1：菜单，2：按钮，3：其他
     */
    private Integer type;
    /**
     * 状态，1：正常，0：冻结
     */
    private Integer status;
    /**
     * 权限在当前模块下的顺序，由小到大
     */
    private Integer seq;
    /**
     * 备注
     */
    private String remark;
    /**
     *
     */
    private Date createdAt;
    /**
     *
     */
    private Date updatedAt;

    private Integer parentId;

    @TableField(exist = false)
    private List<SPermissions> children = new ArrayList<>();
}

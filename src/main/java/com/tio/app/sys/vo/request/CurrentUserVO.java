package com.tio.app.sys.vo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class CurrentUserVO {
    private String id;

    private Integer role;

    private String permissions;

    @TableField(exist = false)
    private String base64;
}

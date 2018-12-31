package com.tio.app.sys.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SAdminHasPermissions {
    private Integer permissionId;
    private Integer sAdminId;
    private String modelType;

    @TableField(exist = false)
    private List<SPermissions> permissions;
}

package com.tio.app.sys.entities;

import lombok.Data;

@Data
public class SAdminHasRoles {
    private Integer roleId;
    private Integer sAdminId;
    private String modelType;
}

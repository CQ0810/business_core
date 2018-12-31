package com.tio.app.sys.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SRoleHasPermissions {
    private Integer permissionId;

    private Integer roleId;
}

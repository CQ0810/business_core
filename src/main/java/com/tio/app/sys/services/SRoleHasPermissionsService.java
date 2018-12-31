package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SRoleHasPermissions;

public interface SRoleHasPermissionsService extends IService<SRoleHasPermissions> {
    /**
     * 更新角色权限
     *
     * @param sRoleId
     * @param permissionsArray
     * @return
     */
    ResultUtil updateRolePermissions(int sRoleId, Integer[] permissionsArray);
}

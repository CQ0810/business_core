package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdminHasPermissions;
import com.tio.app.sys.entities.SPermissions;

import java.util.List;

public interface SAdminHasPermissionsService extends IService<SAdminHasPermissions> {
    List<SPermissions> permissions(Integer sAdminId);

    /**
     * 更新用户权限
     *
     * @param sAdminId
     * @param permissionsArray
     * @return
     */
    ResultUtil updateAdminPermissions(int sAdminId, Integer[] permissionsArray);
}

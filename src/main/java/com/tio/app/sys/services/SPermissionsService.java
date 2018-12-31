package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SPermissions;
import com.tio.app.sys.vo.request.CurrentUserVO;

import java.util.List;

public interface SPermissionsService extends IService<SPermissions> {

    /**
     * 获取权限列表树
     *
     * @return
     */
    List<SPermissions> getPermissionTree();

    /**
     * 修改、添加角色权限
     *
     * @param currentUserVO currentUserVO.getPermissions()  权限ID 1,2,3,4      currentUserVO.getRole()   角色ID
     * @return
     */
    ResultUtil saveOrUpdateRolePermission(CurrentUserVO currentUserVO);
}


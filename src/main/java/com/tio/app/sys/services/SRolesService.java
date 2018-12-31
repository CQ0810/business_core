package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SRoles;
import com.tio.app.sys.vo.request.CurrentUserVO;

import java.util.Set;

public interface SRolesService extends IService<SRoles> {

    /**
     * 获取角色权限（通过用户ID）
     *
     * @param userId
     * @return
     */
    Set<String> getPermissions(long userId);

    /**
     * 获取用户的角色（通过用户ID）
     *
     * @param userId
     * @return
     */
    Set<String> getRoles(long userId);

    /**
     * 获取角色集合
     *
     * @return
     */
    ResultUtil getRoleList(SRoles sRoles);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    ResultUtil deleteRole(CurrentUserVO id);
    
    /**
     * 添加角色
     *
     * @param sRoles
     * @return
     */
    ResultUtil saveRole(SRoles sRoles);


    /**
     * 修改角色
     *
     * @param sRoles
     * @return
     */
    ResultUtil updateRole(SRoles sRoles);
}

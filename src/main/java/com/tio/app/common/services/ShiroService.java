package com.tio.app.common.services;

import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.entities.SRoles;

import java.util.List;
import java.util.Set;

public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 获取用户角色列表
     */
    Set<String> getUserRoles(long userId);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     */
    SAdmin getUser(int userId);

    /**
     * 获取用户角色 列表
     * @param
     * @return
     */
    Set<String> getRolesList();

    /**
     * 根据角色获取权限
     * @param role 角色ID
     * @return
     */
    Set<String> getRolePermissions(long role);

}
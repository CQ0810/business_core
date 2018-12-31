package com.tio.app.common.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tio.app.common.services.ShiroService;
import com.tio.app.sys.entities.*;
import com.tio.app.sys.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author likui
 * @create 2018-12-25
 **/
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SAdminMapper sAdminMapper;

    @Autowired
    private SAdminHasRolesMapper sAdminHasRolesMapper;

    @Autowired
    private SRoleHasPermissionsMapper sRoleHasPermissionsMapper;

    @Autowired
    private SPermissionsMapper sPermissionsMapper;

    @Autowired
    private SRolesMapper sRolesMapper;

    /**
     * 获取用户权限列表
     */
    public Set<String> getUserPermissions(long userId) {
        Set<String> set = new HashSet<>();
        SAdminHasRoles sda = sAdminHasRolesMapper.selectOne(new QueryWrapper<SAdminHasRoles>().eq("s_admin_id", Integer.parseInt(String.valueOf(userId))));
        List<SRoleHasPermissions> list = sRoleHasPermissionsMapper.selectList(new QueryWrapper<SRoleHasPermissions>().eq("role_id", sda.getRoleId()));
        for (SRoleHasPermissions sro : list) {
            SPermissions spe = sPermissionsMapper.selectById(sro.getPermissionId());
            set.add(spe.getName());
        }
        return set;
    }


    /**
     * 获取用户角色列表
     */
    public Set<String> getUserRoles(long userId) {
        Set<String> set = new HashSet<>();
        SAdmin admin = sAdminMapper.selectById(userId);
        List<SAdminHasRoles> listRoles = sAdminHasRolesMapper.selectList(new QueryWrapper<SAdminHasRoles>().eq("s_admin_id", admin.getId()));
        for(SAdminHasRoles asd:listRoles){
            SRoles roles = sRolesMapper.selectById(asd.getRoleId());
            set.add(roles.getName());
        }
        return set;
    }

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     */
    public SAdmin getUser(int userId) {
        SAdmin admin = sAdminMapper.selectById(userId);
        return admin;
    }

    /**
     * 获取角色 列表
     *
     * @return
     */
    @Override
    public Set<String> getRolesList() {
        Set<String> set = new HashSet<>();
        List<SRoles> roles = sRolesMapper.selectList(new QueryWrapper<SRoles>().eq("status", 1));
        for(SRoles sr:roles){
            set.add(sr.getName());
        }
        return set;
    }

    /**
     * 根据角色获取权限
     *
     * @param role
     * @return
     */
    @Override
    public Set<String> getRolePermissions(long role) {
        Set<String> set = new HashSet<>();
        List<SRoleHasPermissions> list = sRoleHasPermissionsMapper.selectList(new QueryWrapper<SRoleHasPermissions>().eq("role_id", role));
        for (SRoleHasPermissions sro : list) {
            SPermissions spe = sPermissionsMapper.selectById(sro.getPermissionId());
            set.add(spe.getName());
        }
        return set;
    }
}

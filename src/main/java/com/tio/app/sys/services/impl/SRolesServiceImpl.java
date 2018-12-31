package com.tio.app.sys.services.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.common.utils.TimeUtil;
import com.tio.app.sys.entities.SAdminHasRoles;
import com.tio.app.sys.entities.SPermissions;
import com.tio.app.sys.entities.SRoleHasPermissions;
import com.tio.app.sys.entities.SRoles;
import com.tio.app.sys.mappers.SRoleHasPermissionsMapper;
import com.tio.app.sys.mappers.SRolesMapper;
import com.tio.app.sys.vo.request.CurrentUserVO;
import com.tio.app.sys.vo.response.SAdminVO;
import com.tio.app.sys.services.SAdminHasRolesService;
import com.tio.app.sys.services.SPermissionsService;
import com.tio.app.sys.services.SRoleHasPermissionsService;
import com.tio.app.sys.services.SRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SRolesServiceImpl extends ServiceImpl<SRolesMapper, SRoles> implements SRolesService {

    @Autowired
    private SPermissionsService sPermissionsService;

    @Autowired
    private SRoleHasPermissionsService sRoleHasPermissionsService;

    @Autowired
    private SAdminHasRolesService sAdminHasRolesService;

    @Autowired
    private SRolesMapper sRolesMapper;
    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> getRoles(long userId) {
        QueryWrapper<SAdminHasRoles> wrapper = new QueryWrapper<>();
        wrapper.eq("s_admin_id", userId);
        List<SAdminHasRoles> list = sAdminHasRolesService.list(wrapper);
        ArrayList<Integer> arrayList = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();
        if (null != list) {
            for (SAdminHasRoles obj : list) {
                arrayList.add(obj.getRoleId());
            }
            Collection<SRoles> roles = listByIds(arrayList);
            if (null != roles) {
                for (SRoles role : roles) {
                    hashSet.add(role.getName());
                }
            }
        }
        return hashSet;
    }

    /**
     * 获取角色的权限列表
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> getPermissions(long userId) {
        return getRoleHasPermissions(getRoleIdByUserId(userId));
    }

    /**
     * 通过用户ID获取角色ID
     *
     * @param userId
     * @return
     */
    private int getRoleIdByUserId(long userId) {
        QueryWrapper<SAdminHasRoles> wrapper = new QueryWrapper<>();
        wrapper.eq("s_admin_id", userId);
        SAdminHasRoles adminHasRole = sAdminHasRolesService.getOne(wrapper);
        return adminHasRole.getRoleId();
    }

    /**
     * 获取角色所有的权限
     *
     * @param roleId
     * @return
     */
    private Set<String> getRoleHasPermissions(long roleId) {
        QueryWrapper<SRoleHasPermissions> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<SRoleHasPermissions> list = sRoleHasPermissionsService.list(wrapper);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (SRoleHasPermissions obj : list) {
            arrayList.add(obj.getPermissionId());
        }
        Collection<SPermissions> sPermissions = sPermissionsService.listByIds(arrayList);
        Set<String> retData = new HashSet<>();
        if (null != sPermissions) {
            for (SPermissions obj : sPermissions) {
                retData.add(obj.getName());
            }
        }
        return retData;
    }

    @Override
    public ResultUtil getRoleList(SRoles sRoles) {
        if (sRoles != null && sRoles.getPage() != null && sRoles.getSize() != null) {
            Page<SRoles> page = new Page<SRoles>(sRoles.getPage(), sRoles.getSize());
            IPage<SRoles> list = sRolesMapper.selectPage(page, new QueryWrapper<SRoles>().
                    orderByDesc("id"));
            return ResultUtil.ok().putData(list);
        }
        List<SRoles> list = sRolesMapper.selectList(new QueryWrapper<SRoles>().orderByDesc("id"));
        return ResultUtil.ok().putData(list);
    }

    @Override
    public ResultUtil deleteRole(CurrentUserVO iDEntity) {
        String[] str = iDEntity.getId().split(",");
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("1")) {
                return ResultUtil.error("管理员角色不能被删除");
            } else {
                sRolesMapper.deleteById(Integer.parseInt(str[i]));
            }
        }
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil saveRole(SRoles sRoles) {
        if(StringUtils.isEmpty(sRoles.getName())){
            return ResultUtil.error("角色名不能为空");
        }
        if(!sRoles.getName().matches("[\\d|\\w]{6,12}")){
            return ResultUtil.error("请输入6-12位字母或数字");
        }
        sRoles.setType(2);
        sRoles.setStatus(1);
        sRoles.setCreatedAt(TimeUtil.getCurrentUnixTimestamp());
        sRolesMapper.insert(sRoles);
        return ResultUtil.ok();
    }


    @Override
    public ResultUtil updateRole(SRoles sRoles) {
        if (sRoles.getId() == 1) {
            return ResultUtil.error("超级管理员不能被操作");
        }
        if(StringUtils.isEmpty(sRoles.getName())){
            return ResultUtil.error("角色名不能为空");
        }
        if(!sRoles.getName().matches("[\\d|\\w]{6,12}")){
            return ResultUtil.error("请输入6-12位字母或数字");
        }
        SRoles se = sRolesMapper.selectById(sRoles.getId());
        se.setName(sRoles.getName());
        se.setRemark(sRoles.getRemark());
        sRolesMapper.updateById(se);
        return ResultUtil.ok();
    }
}

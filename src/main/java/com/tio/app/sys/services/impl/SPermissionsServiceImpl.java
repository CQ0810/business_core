package com.tio.app.sys.services.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdminHasPermissions;
import com.tio.app.sys.entities.SAdminHasRoles;
import com.tio.app.sys.entities.SPermissions;
import com.tio.app.sys.entities.SRoleHasPermissions;
import com.tio.app.sys.mappers.SAdminHasPermissionsMapper;
import com.tio.app.sys.mappers.SAdminHasRolesMapper;
import com.tio.app.sys.mappers.SPermissionsMapper;
import com.tio.app.sys.mappers.SRoleHasPermissionsMapper;
import com.tio.app.sys.vo.request.CurrentUserVO;
import com.tio.app.sys.services.SPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author britton chen
 * @email <britton@126.com>
 */
@Service
public class SPermissionsServiceImpl extends ServiceImpl<SPermissionsMapper, SPermissions> implements SPermissionsService {

    @Autowired
    private SRoleHasPermissionsMapper sRoleHasPermissionsMapper;

    @Autowired
    private SAdminHasRolesMapper sAdminHasRolesMapper;

    @Autowired
    private SAdminHasPermissionsMapper sAdminHasPermissionsMapper;

    @Override
    public ResultUtil saveOrUpdateRolePermission(CurrentUserVO currentUserVO) {
        String permissions = currentUserVO.getPermissions();
        Integer role = currentUserVO.getRole();
        if (role == null) {
            return ResultUtil.error("角色不存在");
        }
        if (role == 1) {
            return ResultUtil.error("超级管理员不能修改");
        }
        if (StringUtils.isEmpty(permissions)) {
            sRoleHasPermissionsMapper.delete(new QueryWrapper<SRoleHasPermissions>().eq("role_id", role));
        } else {
            String[] str = permissions.split(",");
            sRoleHasPermissionsMapper.delete(new QueryWrapper<SRoleHasPermissions>().eq("role_id", role));
            for (int i = 0; i < str.length; i++) {
                SRoleHasPermissions sro = new SRoleHasPermissions();
                sro.setPermissionId(Integer.parseInt(str[i]));
                sro.setRoleId(role);
                sRoleHasPermissionsMapper.insert(sro);
            }
            //            角色用户关联数据
            List<SAdminHasRoles> list=sAdminHasRolesMapper.selectList(new QueryWrapper<SAdminHasRoles>().eq("",role));
            for(SAdminHasRoles sa:list){
                SAdminHasPermissions adminHasPermissions=new SAdminHasPermissions();
                adminHasPermissions.setSAdminId(sa.getSAdminId());
                for (int i = 0; i < str.length; i++) {
                    adminHasPermissions.setPermissionId(Integer.parseInt(str[i]));
                    sAdminHasPermissionsMapper.insert(adminHasPermissions);
                }
            }
        }
        return ResultUtil.ok("OK");
    }

    /**
     * 获取权限列表树
     *
     * @return
     */
    public List<SPermissions> getPermissionTree() {
        QueryWrapper<SPermissions> queryWrapper = new QueryWrapper<>();
        List<SPermissions> dealPermission = baseMapper.selectList(queryWrapper);//从缓存或数据库中查询全部
        //JDK8的stream处理,把根分类区分出来
        List<SPermissions> roots = dealPermission.stream().filter(dealCategory -> (dealCategory.getParentId() == 0)).collect(Collectors.toList());
        //对跟分类进行排序
        roots.sort((SPermissions o1, SPermissions o2) -> o1.getSeq() > o2.getSeq() ? 1 : -1);
        //把非根分类区分出来
        List<SPermissions> subs = dealPermission.stream().filter(dealCategory -> (dealCategory.getParentId() != 0)).collect(Collectors.toList());
        //递归构建结构化的分类信息
        roots.forEach(root -> buildSubs(root, subs));
        return roots;
    }

    /**
     * 递归构建
     *
     * @param parent
     * @param subs
     */
    private void buildSubs(SPermissions parent, List<SPermissions> subs) {
        List<SPermissions> children = subs.stream().filter(sub -> (sub.getParentId() == parent.getId())).collect(Collectors.toList());
        parent.setChildren(children);
        if (!CollectionUtils.isEmpty(children)) {//有子分类的情况
            children.forEach(child -> buildSubs(child, subs));//再次递归构建
        }
    }
}

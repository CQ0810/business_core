package com.tio.app.sys.services.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.utils.CommonUtil;
import com.tio.app.common.utils.RandomStrUtils;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.generate.services.SMediaLibraryService;
import com.tio.app.sys.entities.*;
import com.tio.app.sys.mappers.*;
import com.tio.app.sys.services.SAdminExtraService;
import com.tio.app.sys.services.SAdminService;
import com.tio.app.sys.vo.request.CurrentUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SAdminServiceImpl extends ServiceImpl<SAdminMapper, SAdmin> implements SAdminService {

    @Autowired
    private SAdminMapper sAdminMapper;

    @Autowired
    private SAdminHasRolesMapper sAdminHasRolesMapper;

    @Autowired
    private SRoleHasPermissionsMapper sRoleHasPermissionsMapper;

    @Autowired
    private SAdminHasPermissionsMapper sAdminHasPermissionsMapper;

    @Autowired
    private SRolesMapper sRolesMapper;

    @Autowired
    private SAdminExtraService sAdminExtraService;

    @Autowired
    private SMediaLibraryService sMediaLibraryService;

    @Override
    public ResultUtil saveAdmin(SAdmin sAdmin) {
        ResultUtil resultUtil = checkAdminInfo(sAdmin);
        if (resultUtil != null) {
            return resultUtil;
        }
        if (StringUtils.isEmpty(sAdmin.getUserName())) {
            return ResultUtil.error("用户名为空");
        }
        if (sAdmin.getRoles() == null) {
            return ResultUtil.error("管理员角色为空");
        }
        if (!sAdmin.getPassword().matches("[\\d|\\w]{6,12}")) {
            return ResultUtil.error("请输入6到12位密码，支持大小写英文和数字");
        }
        List<SAdmin> list = sAdminMapper.selectList(new QueryWrapper<SAdmin>().eq("user_name", sAdmin.getUserName()).ne("status", 2));
        if (!list.isEmpty()) {
            return ResultUtil.error(ResultCode.USER_NAME_EXISTS);
        }
        String salt = RandomStrUtils.getInstance().getRandomString(RandomStrUtils.INT_TYPE, 8);
        String md5 = CommonUtil.MD5(sAdmin.getPassword() + salt);
        sAdmin.setSalt(salt);
        sAdmin.setPassword(md5);
        sAdmin.setStatus(1);
        sAdminMapper.insert(sAdmin);//添加用户
        SAdminHasRoles sad = new SAdminHasRoles();
        sad.setRoleId(sAdmin.getRoles());
        sad.setSAdminId(sAdmin.getId());
        sAdminHasRolesMapper.insert(sad);//添加角色和用户关联表
        //该角色下的权限
        List<SRoleHasPermissions> sroList = sRoleHasPermissionsMapper.selectList(new QueryWrapper<SRoleHasPermissions>().eq("role_id", sAdmin.getRoles()));
        if (sroList != null || sroList.size() != 0) {
            for (SRoleHasPermissions sr : sroList) {
                //实例化用户权限关系表
                SAdminHasPermissions asdf = new SAdminHasPermissions();
                asdf.setSAdminId(sAdmin.getId());
                asdf.setPermissionId(sr.getPermissionId());
                sAdminHasPermissionsMapper.insert(asdf);
            }
        }
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil updateAdmin(SAdmin sAdmin) {
        ResultUtil resultUtil = checkAdminInfo(sAdmin);
        if (resultUtil != null) {
            return resultUtil;
        }
        if (sAdmin.getRoles() == null) {
            return ResultUtil.error("管理员角色为空");
        }
        List<SAdmin> list = sAdminMapper.selectList(new QueryWrapper<SAdmin>().eq("user_name", sAdmin.getUserName()).ne("status", 2));
        if (list.isEmpty() || (list.size() == 1 && list.get(0).getId() == sAdmin.getId())) {
            SAdmin as = sAdminMapper.selectById(sAdmin.getId());
            as.setNickName(sAdmin.getNickName());
            if (!StringUtils.isEmpty(sAdmin.getPassword())) {
                as.setPassword(CommonUtil.MD5(sAdmin.getPassword()));
            }
            as.setRemark(sAdmin.getRemark());
            if (sAdmin.getId() != 1) {
                //原来的角色和用户关联实体
                sAdminHasRolesMapper.delete(new QueryWrapper<SAdminHasRoles>().eq("s_admin_id", as.getId()));
                SAdminHasRoles asd = new SAdminHasRoles();
                asd.setSAdminId(as.getId());
                asd.setRoleId(sAdmin.getRoles());
                sAdminHasRolesMapper.insert(asd);
            }
            sAdminMapper.updateById(as);
        } else if (list.size() == 1 && list.get(0).getId() != sAdmin.getId()) {
            return ResultUtil.error("当前用户名已存在");
        } else if (list.size() > 0) {
            return ResultUtil.error("当前用户名已存在");
        }
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil getAdminList(SAdmin sAdmin) {
        if (sAdmin.getPage() != null && sAdmin.getSize() != null) {
            Page page = new Page<SAdmin>(sAdmin.getPage(), sAdmin.getSize());
            IPage<SAdmin> list = sAdminMapper.selectPage(page, new QueryWrapper<SAdmin>().
                    ne("status", 2).
                    like(!StringUtils.isEmpty(sAdmin.getUserName()), "user_name", sAdmin.getUserName()).
                    like(!StringUtils.isEmpty(sAdmin.getNickName()), "nick_name", sAdmin.getNickName()).
                    eq(sAdmin.getStatus() != null, "status", sAdmin.getStatus()).
                    lt(!StringUtils.isEmpty(sAdmin.getEndTime()), "create_date", sAdmin.getEndTime())
                    .orderByDesc("create_date"));
            List<SAdmin> admins = list.getRecords();
            for (SAdmin admin : admins) {
                SAdminHasRoles sAdminHasRoles = sAdminHasRolesMapper.selectOne(new QueryWrapper<SAdminHasRoles>().eq("s_admin_id", admin.getId()));
                if (sAdminHasRoles == null) {
                    continue;
                }
                SRoles sr = sRolesMapper.selectById(sAdminHasRoles.getRoleId());
                if (sr != null) {
                    admin.setRolesName(sr.getName());
                }
            }
            return ResultUtil.ok().putData(list);
        }
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil updateIsNo(CurrentUserVO currentUserVO) {
        SAdmin as = sAdminMapper.selectById(Integer.parseInt(currentUserVO.getId()));
        if (as.getId() == 1) {
            return ResultUtil.error("管理员账号不能修改");
        } else {
            if (as.getStatus() == 0) {
                as.setStatus(1);
            } else {
                as.setStatus(0);
            }
        }
        sAdminMapper.updateById(as);
        return ResultUtil.ok("成功");
    }

    @Override
    public ResultUtil deleteAdmin(CurrentUserVO currentUserVO) {
        if (currentUserVO != null) {
            String[] str = currentUserVO.getId().split(",");
            for (int i = 0; i < str.length; i++) {
                if (str[i].equals("1")) {
                    return ResultUtil.error("超级管理员不能删除");
                } else {
                    SAdmin as = sAdminMapper.selectById(Integer.parseInt(str[i]));
                    as.setStatus(2);
                    sAdminMapper.updateById(as);
                }
            }
        } else {
            return ResultUtil.error("删除错误");
        }
        return ResultUtil.ok("删除成功");
    }

    @Override
    public ResultUtil adminDetail(CurrentUserVO id) {
        SAdmin as = sAdminMapper.selectById(Integer.parseInt(id.getId()));
        SAdminHasRoles asd = sAdminHasRolesMapper.selectOne(new QueryWrapper<SAdminHasRoles>().eq("s_admin_id", Integer.parseInt(id.getId())));
        as.setSAdminHasRoles(asd);
        as.setPassword(null);
        Map<String, Object> map = new HashMap<>();
        return ResultUtil.ok().putData(map);
    }

    @Override
    public String permissionsAdminList(CurrentUserVO currentUserVO) {
        List<SRoleHasPermissions> list = sRoleHasPermissionsMapper.selectList(new QueryWrapper<SRoleHasPermissions>().eq("role_id", currentUserVO.getId()));
        String strRole = "";
        if (list != null && list.size() != 0) {
            for (SRoleHasPermissions sro : list) {
                strRole += sro.getPermissionId() + ",";
            }
            strRole = strRole.substring(0, strRole.length() - 1);
        }
        return strRole;
    }

    @Override
    public ResultUtil addAdmin(SAdmin sAdmin, SAdminExtra sAdminExtra) {
        String salt = RandomStrUtils.getInstance().getRandomString(RandomStrUtils.INT_TYPE, 8);
        String encryptPassword = CommonUtil.MD5(sAdmin.getPassword() + salt);
        sAdmin.setPassword(encryptPassword);
        sAdmin.setSalt(salt);
        saveOrUpdate(sAdmin);
        Integer sAdminId = sAdmin.getId();
        sAdminExtra.setSAdminId(sAdminId);
        sAdminExtraService.saveOrUpdate(sAdminExtra);
        sMediaLibraryService.updateModelId(sAdmin.getId(), sAdminExtra.getImgCard(), sAdminExtra.getImgProxy());
        return ResultUtil.ok().putData(ResultCode.OK);
    }

    /**
     * 验证管理员账户信息
     *
     * @param sAdmin
     * @return
     */
    private ResultUtil checkAdminInfo(SAdmin sAdmin) {
        if (sAdmin == null) {
            return ResultUtil.error("管理员不能为空");
        }
        if (StringUtils.isEmpty(sAdmin.getNickName()) || StringUtils.isEmpty(sAdmin.getPassword())) {
            return ResultUtil.error("nickName为空");
        }
        return null;
    }

    public static void main(String[] args) {
        Set<Object> objects = new HashSet<>();
        objects.add(12);
        objects.add(13);
        String s1 = Convert.toStr(objects).replaceAll("\\[|\\]", "");
        System.out.println(s1);
    }
}

package com.tio.app.sys.controllers.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.entities.SAdminHasPermissions;
import com.tio.app.sys.services.SAdminHasPermissionsService;
import com.tio.app.sys.services.SAdminHasRolesService;
import com.tio.app.sys.services.SAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/test")
public class SysTestController {
    @Autowired
    private SAdminService sAdminService;

    @Autowired
    private SAdminHasPermissionsService sAdminHasPermissionsService;

    @Autowired
    private SAdminHasRolesService sAdminHasRolesService;

    @GetMapping("/list")
    public ResultUtil test() {
        SAdmin obj = sAdminService.getOne(new QueryWrapper<SAdmin>().eq("id", 1));
        obj.setPermissions(sAdminHasPermissionsService.permissions(1));
        obj.setRolesk(sAdminHasRolesService.roles(1));
        List<SAdminHasPermissions> xx = new ArrayList<>();
        SAdminHasPermissions p1 = new SAdminHasPermissions();
        p1.setPermissionId(1);
        p1.setSAdminId(1);
        SAdminHasPermissions p2 = new SAdminHasPermissions();
        p2.setPermissionId(2);
        p2.setSAdminId(1);
        SAdminHasPermissions p3 = new SAdminHasPermissions();
        p1.setPermissionId(3);
        p1.setSAdminId(1);
        xx.add(p1);
        xx.add(p2);
        xx.add(p3);
        sAdminHasPermissionsService.saveOrUpdateBatch(xx);
        return ResultUtil.ok().putData(obj);
    }
}

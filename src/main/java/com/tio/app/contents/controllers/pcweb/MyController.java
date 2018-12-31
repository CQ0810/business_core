package com.tio.app.contents.controllers.pcweb;

import cn.hutool.core.convert.Convert;
import com.tio.app.generate.config.UploadConstant;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.common.utils.UploadUtil;
import com.tio.app.generate.entities.SMediaLibrary;
import com.tio.app.generate.services.SMediaLibraryService;
import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.entities.SAdminExtra;
import com.tio.app.sys.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pcweb/my-test")
public class MyController {
    @Autowired
    private SAdminService sAdminService;

    @Autowired
    private SAdminHasPermissionsService sAdminHasPermissionsService;

    @Autowired
    private SAdminHasRolesService sAdminHasRolesService;

    @Autowired
    private SPermissionsService sPermissionsService;

    @Autowired
    private SRoleHasPermissionsService sRoleHasPermissionsService;

    @Autowired
    private SAdminExtraService sAdminExtraService;

    @Autowired
    private UploadConstant uploadConstant;

    @Autowired
    private SMediaLibraryService sMediaLibraryService;

    /**
     * 更新用户权限
     *
     * @return
     */
    @PostMapping("/update-admin-permissions")
    public ResultUtil updateAdminPermissions(String permissionsIds, int sAdminId) {
        Integer[] permissionsArray = Convert.toIntArray(permissionsIds.split(","));
        return sAdminHasPermissionsService.updateAdminPermissions(sAdminId, permissionsArray);
    }

    /**
     * 更新角色权限
     *
     * @return
     */
    @PostMapping("/update-role-permissions")
    public ResultUtil updateRolePermissions(String permissionsIds, int roleId) {
        Integer[] permissionsArray = Convert.toIntArray(permissionsIds.split(","));
        return sRoleHasPermissionsService.updateRolePermissions(roleId, permissionsArray);
    }

    /**
     * 保存或者更新管理员信息
     *
     * @param sAdmin
     * @param sAdminExtra
     * @return
     */
    @PostMapping("/save-or-update-admin")
    public ResultUtil addAdmin(SAdmin sAdmin, SAdminExtra sAdminExtra) {
        System.out.println(sAdmin);
        System.out.println(sAdminExtra);
        return sAdminService.addAdmin(sAdmin, sAdminExtra);
    }

    @PostMapping("/upload-file")
    public ResultUtil uploadFile(@RequestParam("file") MultipartFile file) {
        return sMediaLibraryService.uploadFile(file, "modelType", "collectionName", "[]", 0);
    }
}

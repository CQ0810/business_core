package com.tio.app.sys.controllers.admin;

import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SPermissions;
import com.tio.app.sys.services.SPermissionsService;
import com.tio.app.sys.vo.request.CurrentUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/sys/admin/permissions")
@RestController
@Slf4j
public class PermissionsController {

    @Autowired
    private SPermissionsService sPermissionsService;

    /**
     * 修改、添加角色权限
     *
     * @return
     */
//    @RequiresPermissions("permissions-set-up")
    @PostMapping(value = "/save-role-permissions")
    public ResultUtil saveRoleJurisdiction(@RequestBody CurrentUserVO currentUserVO) {
        try {
            return sPermissionsService.saveOrUpdateRolePermission(currentUserVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("系统错误" + e.getMessage());
        }
    }

    /**
     * 获取权限列表树
     *
     * @return
     */
    @PostMapping(value = "/permissions-list")
    public ResultUtil permissionsList() {
        try {
            List<SPermissions> permissionTree = sPermissionsService.getPermissionTree();
            return ResultUtil.ok().putData(permissionTree);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(e.getMessage());
        }
    }
}

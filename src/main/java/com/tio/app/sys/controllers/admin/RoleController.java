package com.tio.app.sys.controllers.admin;

import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SRoles;
import com.tio.app.sys.services.SRolesService;
import com.tio.app.sys.vo.request.CurrentUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/sys/admin/role")
@RestController
public class RoleController {

    @Autowired
    private SRolesService roleService;

    /**
     * 获取角色集合
     *
     * @return
     */
    @PostMapping(value = "/get-role-list")
    public ResultUtil getRoleList(@RequestBody SRoles sRoles) {
        try {
            return roleService.getRoleList(sRoles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("系统错误" + e.getMessage());
        }
    }


    /**
     * 删除角色
     * @param id
     * @return
     */
//    @RequiresPermissions("delete-role")
    @PostMapping(value = "/delete-role")
    public ResultUtil deleteRole(@RequestBody CurrentUserVO id) {
        try {
            return roleService.deleteRole(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("系统错误" + e.getMessage());
        }
    }

    /**
     * 添加角色
     * @param sRoles
     * @return
     */
//    @RequiresPermissions("role-save-or-update")
    @PostMapping(value = "/save-role")
    public ResultUtil saveRole(@RequestBody SRoles sRoles) {
        try {
            return roleService.saveRole(sRoles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("系统错误" + e.getMessage());
        }
    }


    /**
     * 修改角色
     * @param sRoles
     * @return
     */
//    @RequiresPermissions("role-save-or-update")
    @PostMapping(value = "/update-role")
    public ResultUtil updateRole(@RequestBody SRoles sRoles) {
        try {
            return roleService.updateRole(sRoles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("系统错误" + e.getMessage());
        }
    }
}

package com.tio.app.sys.controllers.admin;

import com.alibaba.druid.util.StringUtils;
import com.tio.app.common.oauth.TokenManager;
import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.services.ShiroService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.services.SAdminService;
import com.tio.app.sys.vo.request.CurrentUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@RequestMapping(value = "/sys/admin/admin-cloud")
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private SAdminService adminService;

    @Autowired
    private ShiroService shiroService;

    /**
     * 添加管理员
     * @param sAdmin
     * @return
     */
//    @RequiresPermissions("administrator-account-updateOrSave")
    @PostMapping(value = "/save-admin")
    public ResultUtil saveRecruit(@RequestBody SAdmin sAdmin) {
        try {
            return adminService.saveAdmin(sAdmin);
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }

    /**
     * 修改管理员
     * @param sAdmin
     * @return
     */
//    @RequiresPermissions("administrator-account-updateOrSave")
    @PostMapping(value = "/update-admin")
    public ResultUtil updateAdmin(@RequestBody SAdmin sAdmin) {
        try {
            return adminService.updateAdmin(sAdmin);
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }


    /**
     * 获取admin集合
     * @param admin
     * @return
     */
//    @RequiresPermissions("administrator-account-select")
    @PostMapping(value = "/get-admin-list")
    public ResultUtil getAdminList(@RequestBody SAdmin admin) {
        try {
            if (admin == null) {
                return ResultUtil.error(ResultCode.DATA_FORMAT_ERROR);
            }
            return adminService.getAdminList(admin);
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }

    /**
     * 是否启用
     * @param currentUserVO
     * @return
     */
//    @RequiresPermissions("administrator-account-isNo")
    @PostMapping(value = "/update-isNo")
    public ResultUtil updateIsNo(@RequestBody CurrentUserVO currentUserVO) {
        try {
            return adminService.updateIsNo(currentUserVO);
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }

    /**
     * 删除admin
     * @param currentUserVO
     * @return
     */
//    @RequiresPermissions("administrator-account-delete")
    @PostMapping(value = "/delete-admin")
    public ResultUtil deleteAdmin(@RequestBody CurrentUserVO currentUserVO) {
        try {
            return adminService.deleteAdmin(currentUserVO);
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }

    /**
     * admin 详情
     * @param currentUserVO
     * @return
     */
//    @RequiresPermissions("administrator-account-updateOrSave")
    @PostMapping(value = "/admin-detail")
    public ResultUtil adminDetail(@RequestBody CurrentUserVO currentUserVO) {
        try {
            return adminService.adminDetail(currentUserVO);
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }

    /**
     * 获取当前用户的权限列表，逗号分割
     * @param
     * @return
     */
    @PostMapping(value = "/permissions-admin-list")
    public ResultUtil permissionsAdminList(@RequestBody CurrentUserVO currentUserVO) {
        try {
            return ResultUtil.ok(adminService.permissionsAdminList(currentUserVO));
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }

    /**
     * 获取用户权限列表
     * @param httpRequest
     * @return
     */
    @PostMapping(value = "/get-permissions")
    public ResultUtil getJurCode(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("token");
        try {
            if (StringUtils.isEmpty(token)) {
                //获取请求token，如果token不存在，直接返回401 perms
                return ResultUtil.error(ResultCode.TOKEN_NOT_FOUND);
            } else {
                Map<String, Object> tokenMap = TokenManager.decodeToken(token);
                if (tokenMap != null) {
                    String user_id = String.valueOf(tokenMap.get("user_id"));
                    Set<String> set = shiroService.getUserPermissions(Long.valueOf(user_id));
                    return ResultUtil.ok().putData(set);
                }
                return ResultUtil.error(ResultCode.OBTAIN_PERMISSION_ERROR);
            }
        } catch (Exception e) {
            log.error("系统错误" + e.getMessage());
            return ResultUtil.error(ResultCode.COMMON_ERROR);
        }
    }
}

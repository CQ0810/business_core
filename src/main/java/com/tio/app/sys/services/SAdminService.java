package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdmin;
import com.tio.app.sys.entities.SAdminExtra;
import com.tio.app.sys.vo.request.CurrentUserVO;

public interface SAdminService extends IService<SAdmin> {
    /**
     * 添加管理员
     *
     * @param sAdmin
     * @return
     */
    ResultUtil saveAdmin(SAdmin sAdmin);

    /**
     * 修改管理员
     *
     * @param sAdmin
     * @return
     */
    ResultUtil updateAdmin(SAdmin sAdmin);

    /**
     * 查询
     *
     * @param sAdmin
     * @return
     */
    ResultUtil getAdminList(SAdmin sAdmin);

    /**
     * 是否显示
     *
     * @param id
     * @return
     */
    ResultUtil updateIsNo(CurrentUserVO id);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    ResultUtil deleteAdmin(CurrentUserVO id);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    ResultUtil adminDetail(CurrentUserVO id);

    /**
     * 获取当前用户的权限列表，逗号分割
     *
     * @param currentUserVO currentUserVO.getId （角色ID）
     * @return
     */
    String permissionsAdminList(CurrentUserVO currentUserVO);

    /**
     * 新增管理员
     *
     * @param sAdmin
     * @param sAdminExtra
     * @return
     */
    ResultUtil addAdmin(SAdmin sAdmin, SAdminExtra sAdminExtra);
}

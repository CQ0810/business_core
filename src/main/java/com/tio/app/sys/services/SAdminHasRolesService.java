package com.tio.app.sys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.sys.entities.SAdminHasRoles;
import com.tio.app.sys.entities.SRoles;

import java.util.List;

public interface SAdminHasRolesService extends IService<SAdminHasRoles> {
    List<SRoles> roles(Integer sAdminId);
}

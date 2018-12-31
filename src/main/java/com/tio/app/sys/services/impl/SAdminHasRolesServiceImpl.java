package com.tio.app.sys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.sys.entities.SAdminHasRoles;
import com.tio.app.sys.entities.SRoles;
import com.tio.app.sys.mappers.SAdminHasRolesMapper;
import com.tio.app.sys.services.SAdminHasRolesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SAdminHasRolesServiceImpl extends ServiceImpl<SAdminHasRolesMapper, SAdminHasRoles> implements SAdminHasRolesService {
    @Override
    public List<SRoles> roles(Integer sAdminId) {
        return baseMapper.roles(sAdminId);
    }
}

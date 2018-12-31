package com.tio.app.sys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SRoleHasPermissions;
import com.tio.app.sys.mappers.SRoleHasPermissionsMapper;
import com.tio.app.sys.services.SRoleHasPermissionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SRoleHasPermissionsServiceImpl extends ServiceImpl<SRoleHasPermissionsMapper, SRoleHasPermissions> implements SRoleHasPermissionsService {
    @Override
    public ResultUtil updateRolePermissions(int sRoleId, Integer[] permissionsArray) {
        ArrayList<SRoleHasPermissions> list = new ArrayList<>();
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("role_id", sRoleId);
        boolean b = removeByMap(conditionMap);
        if (b) {
            for (Integer permissionId : permissionsArray) {
                SRoleHasPermissions roleHasPermissions = new SRoleHasPermissions();
                roleHasPermissions.setPermissionId(permissionId);
                roleHasPermissions.setRoleId(sRoleId);
                list.add(roleHasPermissions);
            }
            boolean batch = saveBatch(list);
            if (!batch) {
                return ResultUtil.error().putData(ResultCode.UPDATE_DATA_ERROR);
            }
        }
        return ResultUtil.ok().putData(permissionsArray);
    }
}

package com.tio.app.sys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.entities.SAdminHasPermissions;
import com.tio.app.sys.entities.SPermissions;
import com.tio.app.sys.mappers.SAdminHasPermissionsMapper;
import com.tio.app.sys.services.SAdminHasPermissionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SAdminHasPermissionsServiceImpl extends ServiceImpl<SAdminHasPermissionsMapper, SAdminHasPermissions> implements SAdminHasPermissionsService {

    @Override
    public List<SPermissions> permissions(Integer sAdminId) {
        System.out.println(baseMapper.permissions(sAdminId));
        return baseMapper.permissions(sAdminId);
    }

    @Override
    public ResultUtil updateAdminPermissions(int sAdminId, Integer[] permissionIds) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("s_admin_id", sAdminId);
        boolean b = removeByMap(hashMap);
        if (b) {
            List<SAdminHasPermissions> list = new ArrayList<>();
            for (Integer permissionId : permissionIds) {
                SAdminHasPermissions adminHasPermissions = new SAdminHasPermissions();
                adminHasPermissions.setPermissionId(permissionId);
                adminHasPermissions.setSAdminId(sAdminId);
                list.add(adminHasPermissions);
            }
            boolean batch = saveBatch(list);
            if (!batch) {
                return ResultUtil.error().putData(ResultCode.UPDATE_DATA_ERROR);
            }
        }
        return ResultUtil.ok().putData(permissionIds);
    }
}

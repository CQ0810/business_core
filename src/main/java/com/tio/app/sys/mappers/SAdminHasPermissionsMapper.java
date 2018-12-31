package com.tio.app.sys.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.sys.builders.SAdminHasPermissionsBuilder;
import com.tio.app.sys.entities.SAdminHasPermissions;
import com.tio.app.sys.entities.SPermissions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SAdminHasPermissionsMapper extends BaseMapper<SAdminHasPermissions> {
    @SelectProvider(type = SAdminHasPermissionsBuilder.class, method = "permissions")
    List<SPermissions> permissions(Integer sAdminId);
}

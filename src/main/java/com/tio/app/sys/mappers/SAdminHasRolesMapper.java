package com.tio.app.sys.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.sys.builders.SAdminHasRolesBuilder;
import com.tio.app.sys.entities.SAdminHasRoles;
import com.tio.app.sys.entities.SRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SAdminHasRolesMapper extends BaseMapper<SAdminHasRoles> {
    @SelectProvider(type = SAdminHasRolesBuilder.class,method = "roles")
    List<SRoles> roles(Integer sAdminId);
}
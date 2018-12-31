package com.tio.app.generate.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.generate.builders.SMediaLibraryBuilder;
import com.tio.app.generate.entities.SMediaLibrary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Map;
import java.util.Set;

@Mapper
public interface SMediaLibraryMapper extends BaseMapper<SMediaLibrary> {
    @UpdateProvider(type = SMediaLibraryBuilder.class, method = "updateModelId")
    int updateModelId(Integer modelId, Integer... where);
}

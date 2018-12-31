package com.tio.app.generate.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.generate.entities.SRegion;
import com.tio.app.generate.mappers.SRegionMapper;
import com.tio.app.generate.services.SRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SRegionServiceImpl extends ServiceImpl<SRegionMapper, SRegion> implements SRegionService {
    @Autowired
    private SRegionMapper sRegionMapper;

    @Override
    public ResultUtil getRegionTree() {
        List<SRegion> provinceList = sRegionMapper.selectList(new QueryWrapper<SRegion>().eq("parent_id", 100000));
        provinceList.forEach(list -> dealChildren(list, provinceList));
        return ResultUtil.ok().putData(provinceList);
    }

    @Override
    public ResultUtil getRegionByType() {
        return null;
    }

    private void dealChildren(SRegion sRegion, List<SRegion> provinceList) {
        List<SRegion> children = sRegionMapper.selectList(new QueryWrapper<SRegion>().eq("parent_id", sRegion.getId()));
        sRegion.setChildren(children);
        if (!CollectionUtils.isEmpty(children)) {//有子分类的情况
            children.forEach(child -> dealChildren(child, provinceList));//再次递归构建
        }
    }
}

package com.tio.app.generate.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.generate.entities.SRegion;

public interface SRegionService extends IService<SRegion> {

    /**
     * 获取省市区(层级)
     *
     * @param
     * @return
     */
    ResultUtil getRegionTree();
    
    /**
     * 获取省市区(类型)
     *
     * @return
     */
    ResultUtil getRegionByType();
}

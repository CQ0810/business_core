package com.tio.app.generate.controllers;

import com.tio.app.common.utils.ResultUtil;
import com.tio.app.generate.services.SRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author likui
 * @create 2018-12-26
 * 省市区控制层
 **/
@RestController
@RequestMapping(value = "/sys/region")
public class RegionController {

    /**
     * 省市区实现类
     */
    @Autowired
    private SRegionService regionService;


    /**
     * 获取省市区（层级）
     * @param
     * @return
     */
    @PostMapping(value = "/get-region")
    public ResultUtil getRegion(){
        try {
            return  regionService.getRegionTree();
        } catch (Exception e) {
            return  ResultUtil.error("系统异常"+e);
        }
    }


    /**
     * 获取省市区(类型)
     * @param
     * @return
     */
    @PostMapping(value = "/get-region-by-type")
    public ResultUtil getRegionByType(){
        try {
            return  regionService.getRegionByType();
        } catch (Exception e) {
            return  ResultUtil.error("系统异常"+e);
        }
    }
}

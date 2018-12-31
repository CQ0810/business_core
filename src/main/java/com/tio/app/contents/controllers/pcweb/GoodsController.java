package com.tio.app.contents.controllers.pcweb;

import com.tio.app.contents.entities.Goods;
import com.tio.app.contents.services.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/pcweb/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/add")
    public int add(@Valid Goods goods) {
        return goodsService.addOrder(goods);
    }
}

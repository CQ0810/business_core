package com.tio.app.contents.services;

import com.tio.app.contents.entities.Goods;
import com.tio.app.contents.entities.Orders;
import com.tio.app.contents.mappers.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Transactional
    public int addOrder(Goods goods) {
        return goodsMapper.insert(goods);
    }


}

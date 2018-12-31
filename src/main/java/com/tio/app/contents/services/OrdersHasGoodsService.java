package com.tio.app.contents.services;

import com.tio.app.contents.entities.OrdersHasGoods;
import com.tio.app.contents.mappers.OrdersHasGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersHasGoodsService {
    @Autowired
    private OrdersHasGoodsMapper ordersHasGoodsMapper;

    @Transactional
    public int add(OrdersHasGoods ordersHasGoods) {
        return ordersHasGoodsMapper.insert(ordersHasGoods);
    }
}

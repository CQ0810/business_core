package com.tio.app.contents.services;

import com.tio.app.contents.entities.Goods;
import com.tio.app.contents.entities.Orders;
import com.tio.app.contents.entities.OrdersHasGoods;
import com.tio.app.contents.mappers.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersHasGoodsService ordersHasGoodsService;

    @Transactional
    public int add(Orders orders) {
        ordersMapper.insert(orders);
        int orderId = orders.getId();
        OrdersHasGoods ordersHasGoods = new OrdersHasGoods();
        ordersHasGoods.setGoodsId(2);
        ordersHasGoods.setOrdersId(9);
        return ordersHasGoodsService.add(ordersHasGoods);
    }

    public Orders getOrderById(Integer id) {
        return ordersMapper.findOrderById(id);
    }
}

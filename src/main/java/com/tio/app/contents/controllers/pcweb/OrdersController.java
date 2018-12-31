package com.tio.app.contents.controllers.pcweb;

import com.tio.app.contents.entities.Orders;
import com.tio.app.contents.services.OrdersService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/pcweb/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/add")
    public int add(@Valid Orders orders) {
        return ordersService.add(orders);
    }

    @GetMapping("/getOrderById")
    public Orders getOrderById(@Param("id") Integer id) {
        return ordersService.getOrderById(id);
    }
}

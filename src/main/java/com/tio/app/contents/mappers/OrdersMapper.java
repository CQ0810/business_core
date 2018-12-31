package com.tio.app.contents.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.contents.builders.OrdersSqlBuilder;
import com.tio.app.contents.entities.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrdersMapper extends BaseMapper<Orders> {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    @SelectProvider(type = OrdersSqlBuilder.class, method = "findOrdersById")
    @Results({
            @Result(property = "ordersHasGoods", column = "id", many = @Many(select = "com.tio.app.contents.mappers.OrdersHasGoodsMapper.getListByOrdersId"))
    })
    Orders findOrderById(Integer id);
}
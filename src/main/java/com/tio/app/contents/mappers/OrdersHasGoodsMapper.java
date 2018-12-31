package com.tio.app.contents.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.contents.builders.OrdersHasGoodsSqlBuilder;
import com.tio.app.contents.entities.OrdersHasGoods;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrdersHasGoodsMapper extends BaseMapper<OrdersHasGoods> {
    int deleteByPrimaryKey(@Param("ordersId") Integer ordersId, @Param("goodsId") Integer goodsId);

    int insert(OrdersHasGoods record);

    int insertSelective(OrdersHasGoods record);

    OrdersHasGoods selectByPrimaryKey(@Param("ordersId") Integer ordersId, @Param("goodsId") Integer goodsId);

    int updateByPrimaryKeySelective(OrdersHasGoods record);

    int updateByPrimaryKey(OrdersHasGoods record);

    @SelectProvider(type = OrdersHasGoodsSqlBuilder.class, method = "findOrdersById")
    @Results({
            @Result(property = "goods", column = "goods_id", one = @One(select = "com.tio.app.contents.mappers.GoodsMapper.findByGoodsId"))
    })
    OrdersHasGoods getListByOrdersId(Integer id);
}
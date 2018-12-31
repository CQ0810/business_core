package com.tio.app.contents.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tio.app.contents.builders.GoodsSqlBuilder;
import com.tio.app.contents.entities.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GoodsMapper extends BaseMapper<Goods> {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    @SelectProvider(type = GoodsSqlBuilder.class, method = "findOrdersById")
    Goods findByGoodsId(Integer Id);
}
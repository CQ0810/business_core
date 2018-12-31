package com.tio.app.contents.entities;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class Goods extends Model<Goods> {
    /* */
    private Integer id;

    /* 商品名称*/
    private String goodsName;

    /* 商品价格*/
    private Float price;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
package com.tio.app.contents.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Orders extends Model<Orders> {
    /* */
    private Integer id;

    /* */
    private Integer userId;

    @TableField(exist = false)
    List<OrdersHasGoods> ordersHasGoods;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
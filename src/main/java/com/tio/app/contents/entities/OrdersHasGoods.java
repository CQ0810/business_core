package com.tio.app.contents.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class OrdersHasGoods {
    /* */
    private Integer ordersId;

    /* */
    private Integer goodsId;

    /* */
    private String remark;

    @TableField(exist = false)
    Goods goods;
}
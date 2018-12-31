package com.tio.app.contents.builders;

import org.apache.ibatis.jdbc.SQL;

public class OrdersHasGoodsSqlBuilder {
    public static String findOrdersById(final Integer id) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("orders_has_goods");
            WHERE("orders_id=#{id}");
        }}.toString();
        System.out.println("sql = " + sql + " " + id);
        return sql;
    }
}

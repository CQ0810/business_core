package com.tio.app.contents.builders;

import org.apache.ibatis.jdbc.SQL;

public class OrdersSqlBuilder {
    public static String findOrdersById(final Integer Id) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("orders");
            WHERE("id=#{Id}");
        }}.toString();
        System.out.println("sql = " + sql + " " + Id);
        return sql;
    }
}

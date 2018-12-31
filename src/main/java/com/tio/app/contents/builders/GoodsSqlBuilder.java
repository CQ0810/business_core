package com.tio.app.contents.builders;

import org.apache.ibatis.jdbc.SQL;

public class GoodsSqlBuilder {
    public static String findOrdersById(final Integer id) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("goods");
            WHERE("id=#{id}");
        }}.toString();
        System.out.println("sql = " + sql + " " + id);
        return sql;
    }
}

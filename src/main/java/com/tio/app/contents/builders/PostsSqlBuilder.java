package com.tio.app.contents.builders;

import org.apache.ibatis.jdbc.SQL;

public class PostsSqlBuilder {
    public static String getPostsByUserId(final Integer userId) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("posts");
            WHERE(" user_id=#{userId}");
        }}.toString();
        System.out.println("aaaaa" + sql);
        return sql;
    }
}

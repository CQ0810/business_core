package com.tio.app.contents.builders;

import org.apache.ibatis.jdbc.SQL;

public class UserSqlBuilder {
    public static String buildGetUsersByName(final String name) {
        String sql = new SQL() {{
            SELECT("u.username ,u.password,p.*");
            FROM("user as u");
            INNER_JOIN("posts as p on p.user_id=u.id ");
            if (name != null) {
                WHERE("username like #{value} || '%'");
            }
            ORDER_BY("id");
        }}.toString();
        System.out.println(sql);
        return sql;
    }


    public static String findUserById(final Integer userId) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("user");
            WHERE("id=#{userId}");
            ORDER_BY("id");
        }}.toString();
        System.out.println(sql);
        return sql;
    }
}

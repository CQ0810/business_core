package com.tio.app.sys.builders;

import org.apache.ibatis.jdbc.SQL;

public class SAdminHasRolesBuilder {
    public static String roles(Integer sAdminId) {
        String sql = new SQL() {{
            SELECT("sr.*");
            FROM("s_roles sr");
            INNER_JOIN("s_admin_has_roles sahr on sr.id=sahr.role_id");
            WHERE("sahr.s_admin_id=#{sAdminId}");
        }}.toString();
        return sql;
    }
}

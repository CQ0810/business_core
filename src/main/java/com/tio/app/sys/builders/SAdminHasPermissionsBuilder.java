package com.tio.app.sys.builders;

import org.apache.ibatis.jdbc.SQL;

public class SAdminHasPermissionsBuilder {
    public static String permissions(Integer sAdminId) {
        String sql = new SQL() {{
            SELECT("sp.*");
            FROM("s_permissions sp");
            INNER_JOIN("s_admin_has_permissions sahp on sp.id = sahp.permission_id");
            WHERE("sahp.s_admin_id = #{sAdminId}");
        }}.toString();
        return sql;
    }
}

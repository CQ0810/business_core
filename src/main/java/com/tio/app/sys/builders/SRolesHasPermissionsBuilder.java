package com.tio.app.sys.builders;

import org.apache.ibatis.jdbc.SQL;

public class SRolesHasPermissionsBuilder {
    public static String buildGetRoleHasPermissions(final Integer roleId) {
        String sql = new SQL() {{
            SELECT("rp.permission_id ,rp.role_id,sp.*");
            FROM("s_role_has_permissions as rp");
            INNER_JOIN("s_permissions sp on sp.id = rp.permission_id ");
            WHERE("  rp.role_id=#{roleId}");
        }}.toString();
        System.out.println(sql);
        return sql;
    }
}

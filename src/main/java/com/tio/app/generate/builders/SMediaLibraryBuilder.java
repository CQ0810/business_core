package com.tio.app.generate.builders;

import cn.hutool.core.convert.Convert;
import org.apache.ibatis.jdbc.SQL;

import java.util.Set;

public class SMediaLibraryBuilder {
    /**
     * 批量更新modelId
     *
     * @param modelId
     * @param where
     * @return
     */
    public static String updateModelId(final Integer modelId, final Integer... where) {
        String condition = Convert.toStr(where).replaceAll("\\[|\\]", "");
        System.out.println(condition);
        String sql = new SQL() {{
            UPDATE("s_media_library");
            SET("model_id=#{modelId}");
            WHERE("id in (" + condition + ")");
        }}.toString();
        System.out.println(sql);
        return sql;
    }
}

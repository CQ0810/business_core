package com.tio.app.common.annotations;

import com.tio.app.common.datasource.DataSourceNames;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * @author zj chen <britton@126.com>
 * @date 2018/9/20 09:37
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceAnnotation {
    String value() default DataSourceNames.MASTER;
}
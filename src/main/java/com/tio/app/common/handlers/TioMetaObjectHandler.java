package com.tio.app.common.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tio.app.common.utils.TimeUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 填充器
 */
@Component
public class TioMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TioMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createdAt", TimeUtil.getCurrentUnixTimestamp(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("updatedAt", TimeUtil.getCurrentUnixTimestamp(), metaObject);
    }
}
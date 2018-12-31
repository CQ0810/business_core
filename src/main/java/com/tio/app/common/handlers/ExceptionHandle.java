package com.tio.app.common.handlers;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

@RestControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 处理MySQL语法错误异常,主要解决的是调用自定义函数时发生的错误
     *
     * @param e
     */
    @ExceptionHandler(value = {MySQLSyntaxErrorException.class, MySQLIntegrityConstraintViolationException.class})
    public void Handle(Exception e) {
        logger.error(e.getMessage(), e);
        System.out.println(e.getMessage());
    }
}

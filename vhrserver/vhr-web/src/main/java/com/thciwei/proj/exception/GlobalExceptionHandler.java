package com.thciwei.proj.exception;

import com.thciwei.proj.bean.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean mySQLException(Exception e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("数据有关联，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");

    }

}

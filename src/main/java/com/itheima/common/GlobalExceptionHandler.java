package com.itheima.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyz
 * @create 2024-06-14-16:55
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    // 异常处理方法
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException e){
        log.info("出现异常！" + e.getMessage());
        return R.error(e.getMessage());
    }

    // 异常处理方法
    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception e){
        log.info("出现异常！" + e.getMessage());
        return R.error("失败！");
    }



}

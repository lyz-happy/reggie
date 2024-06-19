package com.itheima.common;

/**
 * @author lyz
 * @create 2024-06-18-8:17
 * 自定义业务异常类
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }

}

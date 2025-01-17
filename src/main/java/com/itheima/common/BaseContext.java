package com.itheima.common;

import org.springframework.stereotype.Component;

/**
 * @author lyz
 * @create 2024-06-15-9:29
 * 基于ThreadLocal封装工具类，用户保存和获取当前用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}

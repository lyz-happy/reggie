package com.itheima.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lyz
 * @create 2024-06-15-9:07
 * 自定义元数据对象处理器，与@TableField(fill = FieldFill.INSERT)配合使用完成公共字段（createTime、updateTime）自动填充
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("线程id：" + Thread.currentThread().getId());
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("updateTime",new Date());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("线程id：" + Thread.currentThread().getId());
        metaObject.setValue("updateTime",new Date());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}

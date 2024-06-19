package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @TableName setmeal
 * 套餐
 */
@TableName(value ="setmeal")
@Data
public class Setmeal implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    // 所属分类id
    private Long categoryId;

    private String name;

    private BigDecimal price;

    private Integer status;

    private String code;

    private String description;

    private String image;

    // 插入时填充字段，这样使得可以不用在service层补充前端未传送过来的数据        与       commom.MyMetaObjecthandler配合使用
    @TableField(fill = FieldFill.INSERT)
    // 在数据库中存的是date或datetime类型的值。从数据库里取出来遍历到页面上显示的是long类型或是GTM类型的日期时间
    // 以下注解使得前端显示的时间类型数据不再是一串数字，而是时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone= "GMT+8")
    private Date createTime;

    // 插入、更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    // 在数据库中存的是date或datetime类型的值。从数据库里取出来遍历到页面上显示的是long类型或是GTM类型的日期时间
    // 以下注解使得前端显示的时间类型数据不再是一串数字，而是时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone= "GMT+8")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}
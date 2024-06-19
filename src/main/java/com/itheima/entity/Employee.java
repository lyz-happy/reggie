package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @TableName employee
 * 员工
 */
@TableName(value ="employee")
@Data
public class Employee implements Serializable {

    // 此注解目的是后端将Employee转换为JSON对象时，将Long型的id转换为String类型，因为Long型的id前端存储会有精度丢失，导致前端使用id请求后端时找不到对应的数据
    // 再次发送请求时，前端id:"1801540576458940418"，@RequestBody Employee employee 可以接受该字符串，并转换成Long型，存储到id中
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String sex;
    // 身份证
    private String idNumber;

    private Integer status;

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

    private static final long serialVersionUID = 1L;
}
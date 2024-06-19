package com.itheima.dto;

import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

// 前端请求http://localhost:8080/dish（新增菜品）时，现有的实体类无法封装前端传送过来的数据，前端多出菜品口味的集合
@Data
public class DishDto extends Dish {

    // 菜品口味
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}

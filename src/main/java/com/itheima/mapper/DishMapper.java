package com.itheima.mapper;

import com.itheima.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2024-06-18 08:04:08
* @Entity com.itheima.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    Dish recordById(Long id);
}





package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.dto.DishDto;
import com.itheima.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ASUS
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2024-06-18 08:04:08
*/
public interface DishService extends IService<Dish> {
    R<String> addDishAndFlavor(DishDto dishDto);
    R<Page> pageDish(int page, int pageSize, String name);
    R<DishDto> getDish(Long id);

    R<String> updateDishAndFlavor(DishDto dishDto);

    R<List<Dish>> listDish(Long categoryId);
}

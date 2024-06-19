package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.dto.DishDto;
import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import com.itheima.service.DishFlavorService;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lyz
 * @create 2024-06-19-8:05
 * 菜品管理：需要操作菜品dish表、dish_flavor表
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    // 新增菜品：1.发送异步请求查询菜品分类（需要在CategoryController中处理）
    //         2.新增dish
    //         3.新增dish_flavor，其中dish_id在新增dish后的实体类自动回填
    @PostMapping()
    public R<String> addDishAndFlavor(@RequestBody DishDto dishDto){
        dishService.addDishAndFlavor(dishDto);
        return R.success("新增成功");
    }

    // 菜品分页查询，需要展示图片与菜品分类
    @GetMapping("/page")
    public R<Page> pageDish(int page, int pageSize, String name) {
        R<Page> result = dishService.pageDish(page, pageSize, name);
        return result;
    }

    // 修改菜品：菜品以及菜品口味回显
    @GetMapping("/{id}")
    public R<DishDto> getDish(@PathVariable Long id){
        R<DishDto> result = dishService.getDish(id);
        return result;
    }

    // 修改菜品：修改菜品
    @PutMapping()
    public R<String> updateDishAndFlavor(@RequestBody DishDto dishDto){
        dishService.updateDishAndFlavor(dishDto);
        return R.success("修改成功");
    }

    // 根据分类查询菜品
    @GetMapping("/list")
    public R<List<Dish>> listDish(Long categoryId) {
        R<List<Dish>> result = dishService.listDish(categoryId);
        return result;
    }

}

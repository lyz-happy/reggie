package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.dto.SetmealDto;
import com.itheima.service.SetmealDishService;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lyz
 * @create 2024-06-19-14:29
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐：1.展示套餐分类到下拉框（CategoryController已实现）
     *         2.根据菜品分类展示对应菜品（DishController已实现）
     *         3.保存到Setmeal、SetmealDish
     */
    @PostMapping
    public R<String> addSetmealAndSetmealDish (@RequestBody SetmealDto setmealDto){
        R<String> result = setmealService.addSetmealAndSetmealDish(setmealDto);
        return result;
    }

    /**
     * 套餐信息分页查询
     */
    @GetMapping("/page")
    public R<Page> pageSetmeal(int page, int pageSize, String name) {
        R<Page> result = setmealService.pageSetmeal(page,pageSize,name);
        return result;
    }

    // 删除套餐：单个/多个，请求形式查看前端浏览器
    @DeleteMapping()
    public R<String> deleteSetmealAndSetmealDish(@RequestParam List<Long> ids){
        R<String> result = setmealService.deleteSetmealAndSetmealDish(ids);
        return result;
    }

}

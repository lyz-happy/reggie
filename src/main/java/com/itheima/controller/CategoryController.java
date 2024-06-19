package com.itheima.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.entity.Category;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lyz
 * @create 2024-06-18-7:23
 * 分类的增删改查
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 新增分类
    @PostMapping
    public R<String> saveCategory(@RequestBody Category category) {
        R<String> result = categoryService.saveCategory(category);
        return result;
    }

    // 分类信息分页查询
    @GetMapping("/page")
    public R<Page> pageCategory(int page, int pageSize) {
        R<Page> result = categoryService.pageCategory(page, pageSize);
        return result;
    }

    // 根据type条件进行菜品分类查询
    @GetMapping("/list")
    public R<List<Category>> dishCategory(Category category){
        R<List<Category>> result = categoryService.dishCategory(category);
        return result;
    }

    // 删除分类：根据id删除分类
    // 注意：需要检查删除的分类是否关联菜品（Dish）或者套餐（Setmeal）、菜品（Dish）或者套餐（Setmeal）中存在所属分类选项
    @DeleteMapping()
    public R<String> deleteCategory(Long ids) {
        R<String> result = categoryService.deleteCategory(ids);
        return result;
    }

    // 修改分类：分类信息回显（不采用与后端数据交互方式，前端保存着分类数据，进行数据绑定即可） + 提交修改信息
    // 提交修改信息
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {
        R<String> result = categoryService.updateCategory(category);
        return result;
    }

}

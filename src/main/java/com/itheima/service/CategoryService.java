package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ASUS
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2024-06-18 07:21:28
*/
public interface CategoryService extends IService<Category> {

    R<String> saveCategory(Category category);

    R<Page> pageCategory(int page, int pageSize);

    R<String> deleteCategory(Long ids);

    R<String> updateCategory(Category category);

    R<List<Category>> dishCategory(Category category);
}

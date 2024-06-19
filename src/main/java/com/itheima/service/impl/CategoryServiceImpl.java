package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.CustomException;
import com.itheima.common.R;
import com.itheima.entity.Category;
import com.itheima.entity.Dish;
import com.itheima.entity.Setmeal;
import com.itheima.mapper.DishMapper;
import com.itheima.mapper.SetmealMapper;
import com.itheima.service.CategoryService;
import com.itheima.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ASUS
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2024-06-18 07:21:28
 *
*/
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public R<String> saveCategory(Category category) {
        categoryMapper.insert(category);
        return R.success("插入成功");
    }

    // 分页查询
    @Override
    public R<Page> pageCategory(int page, int pageSize) {
        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 条件构造器（前端无关键词传入，我们仅仅设置排序即可）
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        // 分页查询
        categoryMapper.selectPage(pageInfo,queryWrapper);
        // 返回分页数据
        return R.success(pageInfo);
    }

    // 删除分类
    @Override
    public R<String> deleteCategory(Long ids) {
        // 查看菜品表中是否有所有分类，有则无法删除     select count(*) from dish where category_id = ids;
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,ids);
        Integer count = dishMapper.selectCount(queryWrapper);
        if (count > 0) {
            // 抛出业务异常
            throw new CustomException("当前分类项关联菜品，无法删除");
        }
        // 查看套餐表中是否有所有分类，有则无法删除     select count(*) from setmeal where category_id = ids;
        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Setmeal::getCategoryId,ids);
        Integer count1 = setmealMapper.selectCount(queryWrapper1);
        if (count1 > 0) {
            // 抛出业务异常
            throw new CustomException("当前分类项关联套餐，无法删除");
        }
        categoryMapper.deleteById(ids);
        return R.success("删除成功！");
    }

    // 修改分类
    @Override
    public R<String> updateCategory(Category category) {
        categoryMapper.updateById(category);
        return R.success("修改成功");
    }

    @Override
    public R<List<Category>> dishCategory(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return R.success(categories);
    }
}





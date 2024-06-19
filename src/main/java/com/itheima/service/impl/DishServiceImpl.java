package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.itheima.common.R;
import com.itheima.dto.DishDto;
import com.itheima.entity.Category;
import com.itheima.entity.Dish;
import com.itheima.entity.DishFlavor;
import com.itheima.mapper.CategoryMapper;
import com.itheima.mapper.DishFlavorMapper;
import com.itheima.service.DishService;
import com.itheima.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author ASUS
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2024-06-18 08:04:08
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public R<String> addDishAndFlavor(DishDto dishDto) {
        // 新增dish
        dishMapper.insert(dishDto);
        Long id = dishDto.getId();
        // 新增dishFlavor，其中的dish_id在新增dish后自动回填到dishDto中
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(id);
            dishFlavorMapper.insert(flavor);
        }
        return R.success("插入成功");
    }

    // 分页查询
    @Override
    public R<Page> pageDish(int page, int pageSize, String name) {
//        // 分页构造器
//        Page<Dish> pageInfo = new Page<>(page, pageSize);
//        // 条件构造器
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like(name != null, Dish::getName, name);
//        queryWrapper.orderByDesc(Dish::getUpdateTime);
//        // 分页查询
//        dishMapper.selectPage(pageInfo, queryWrapper);
//        // 无法直接return R.success(pageInfo);  pageInfo中展示的是菜品分类id（category_id），我们需要展示菜品分类名称，因此，继续执行
//        // 根据category_id查询菜品分类名称
        // 分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        // 分页查询
        dishMapper.selectPage(pageInfo, queryWrapper);
        // 无法直接return R.success(pageInfo);  pageInfo中展示的是菜品分类id（category_id），我们需要展示菜品分类名称，因此，继续执行
        // 根据category_id查询菜品分类名称
        // 前端需要一个categoryName的字段，public class DishDto extends Dish中有categoryName，可以实现
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);
        // 对象拷贝，除了records记录不拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        //
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishdtoList = new ArrayList<>();
        for (Dish record : records) {
            DishDto dishDto = new DishDto();
            Category category = categoryMapper.selectById(record.getCategoryId());
            dishDto.setCategoryName(category.getName());
            BeanUtils.copyProperties(record,dishDto);
            dishdtoList.add(dishDto);
        }
        dishDtoPage.setRecords(dishdtoList);
        return R.success(dishDtoPage);
    }

    // 修改菜品信息：菜品信息回显
    @Override
    public R<DishDto> getDish(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish,dishDto);
        Category category = categoryMapper.selectById(dish.getCategoryId());
        dishDto.setCategoryName(category.getName());
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(queryWrapper);
        dishDto.setFlavors(dishFlavors);
        return R.success(dishDto);
    }

    // 修改菜品信息：修改菜品
    @Override
    public R<String> updateDishAndFlavor(DishDto dishDto) {
        // 修改dish表
        dishMapper.updateById(dishDto);
        // 修改dishflavor表：删除原有
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorMapper.delete(queryWrapper);
        //修改dishflavor表：增加
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            dishFlavorMapper.insert(flavor);
        }
        return R.success("修改成功");
    }

    @Override
    public R<List<Dish>> listDish(Long categoryId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,categoryId);
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishes = dishMapper.selectList(queryWrapper);
        return R.success(dishes);
    }
}





package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.CustomException;
import com.itheima.common.R;
import com.itheima.dto.SetmealDto;
import com.itheima.entity.Category;
import com.itheima.entity.Setmeal;
import com.itheima.entity.SetmealDish;
import com.itheima.mapper.CategoryMapper;
import com.itheima.mapper.SetmealDishMapper;
import com.itheima.service.SetmealService;
import com.itheima.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author ASUS
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2024-06-18 08:04:09
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;


    @Override
    @Transactional
    public R<String> addSetmealAndSetmealDish(SetmealDto setmealDto) {
        // 保存Setmeal
        setmealMapper.insert(setmealDto);
        // 保存Setmeal：insert完毕之后，自动回填实体类的id
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(Long.toString(setmealDto.getId()));
            setmealDishMapper.insert(setmealDish);
        }
        return R.success("新增成功");
    }

    // 分页查询代码
    @Override
    public R<Page> pageSetmeal(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        Page<Setmeal> selectPage = setmealMapper.selectPage(setmealPage, queryWrapper);
        // 以上查询出来的包含category_id，前端需要展示name，可以放到SetmealDto
        Page<SetmealDto> setmealDtoPagePage = new Page<>(page,pageSize);
        BeanUtils.copyProperties(setmealPage,setmealDtoPagePage,"records");
        List<Setmeal> records = selectPage.getRecords();
        List<SetmealDto> records2 = new ArrayList<>();
        for (Setmeal record : records) {
            SetmealDto setmealDto = new SetmealDto();
            Category category = categoryMapper.selectById(record.getCategoryId());
            BeanUtils.copyProperties(record,setmealDto);
            setmealDto.setCategoryName(category.getName());
            records2.add(setmealDto);
        }
        setmealDtoPagePage.setRecords(records2);
        return R.success(setmealDtoPagePage);
    }

    // 删除套餐
    @Transactional
    @Override
    public R<String> deleteSetmealAndSetmealDish(List<Long> ids) {
        // 删除setmeal
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = setmealMapper.selectCount(setmealLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("无法删除，商品正在售卖！");
        }
        for (Long id : ids) {
            setmealMapper.deleteById(id);
        }
        // 删除setmeal_dish
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishMapper.delete(setmealDishLambdaQueryWrapper);
        return R.success("删除成功！");
    }
}





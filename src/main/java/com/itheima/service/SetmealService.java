package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.dto.SetmealDto;
import com.itheima.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ASUS
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2024-06-18 08:04:09
*/
public interface SetmealService extends IService<Setmeal> {

    R<String> addSetmealAndSetmealDish(SetmealDto setmealDto);

    R<Page> pageSetmeal(int page, int pageSize, String name);

    R<String> deleteSetmealAndSetmealDish(List<Long> ids);
}

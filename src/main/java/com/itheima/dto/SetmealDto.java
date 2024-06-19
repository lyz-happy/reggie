package com.itheima.dto;

import com.itheima.entity.Setmeal;
import com.itheima.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    // 存放前端传送过来的数组
    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

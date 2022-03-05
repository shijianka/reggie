package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.Category;
import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.Setmeal;
import cn.shijianka.reggie.mapper.CategoryMapper;
import cn.shijianka.reggie.service.CategoryService;
import cn.shijianka.reggie.service.DishService;
import cn.shijianka.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除，删除之前先判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件 根据分类id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);


        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1>0){
            //抛出业务异常
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            //抛出业务异常
        }
        //正常删除
    }
}

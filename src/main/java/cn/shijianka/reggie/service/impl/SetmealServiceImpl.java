package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.dto.SetmealDto;
import cn.shijianka.reggie.entity.Setmeal;
import cn.shijianka.reggie.entity.SetmealDish;
import cn.shijianka.reggie.mapper.SetmealMapper;
import cn.shijianka.reggie.service.SetmealDishService;
import cn.shijianka.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐同时保存关联的关系
     *
     * @param setmealDto
     */
    @Transactional
    @Override
    public void addWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息 操作Setmeal 执行insert
        this.save(setmealDto);
        //保存关联信息 操作Setmeal_dish 执行insert
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealDto.getId());
            return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(list);


    }
}

package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.dto.DishDto;
import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.DishFlavor;
import cn.shijianka.reggie.mapper.DishMapper;
import cn.shijianka.reggie.service.DishFlavorService;
import cn.shijianka.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;
    /**
     * 新增菜品同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息
        this.save(dishDto);
        //保存菜品口味到口味表
        Long id = dishDto.getId();//菜品id
        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        List<DishFlavor> collect = flavors.stream().map(n -> {
            n.setDishId(id);
            return n;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(collect);

    }

    /**
     *     根据id查询dish 和dishDto
     * @param id
     * @return
     */
    @Override
    public DishDto getWithFlavor(Long id) {
        Dish dish = dishService.getById(id);
        //获取DishFlavor ? dishId==id
        LambdaQueryWrapper<DishFlavor> lqw=new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(lqw);
        DishDto dishDto = new DishDto();
        //拷贝dish中的数据
        dishDto.setFlavors(list);
        BeanUtils.copyProperties(dish,dishDto);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //获取dishId
        Long dishId = dishDto.getId();
        //根据dishId删除flavor
        LambdaQueryWrapper<DishFlavor> lqw=new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishId);
        dishFlavorService.remove(lqw);

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish);
        //更新dish
        dishService.updateById(dish);
        //获取用户提交的dishFlavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        //给dishFlavor的dishId赋值
        List<DishFlavor> dishFlavorList = flavors.stream().map(dishFlavor -> {
            dishFlavor.setDishId(dishId);
            return dishFlavor;
        }).collect(Collectors.toList());
        //保存新增的dishFlavor
        dishFlavorService.saveBatch(dishFlavorList);
    }
}

package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.dto.DishDto;
import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.DishFlavor;
import cn.shijianka.reggie.mapper.DishMapper;
import cn.shijianka.reggie.service.DishFlavorService;
import cn.shijianka.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
}

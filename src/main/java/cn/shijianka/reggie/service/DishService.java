package cn.shijianka.reggie.service;

import cn.shijianka.reggie.dto.DishDto;
import cn.shijianka.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;



public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish dishFlavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询dish 和dishDto
    DishDto getWithFlavor(Long id);
    //更新菜品信息和口味信息
    void updateWithFlavor(DishDto dishDto);
}

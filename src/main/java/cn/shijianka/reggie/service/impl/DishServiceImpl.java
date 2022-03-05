package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.mapper.DishMapper;
import cn.shijianka.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}

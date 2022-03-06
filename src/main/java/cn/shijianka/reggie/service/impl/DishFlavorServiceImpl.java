package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.DishFlavor;
import cn.shijianka.reggie.mapper.DishFlavorMapper;
import cn.shijianka.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

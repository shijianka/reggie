package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.ShoppingCart;
import cn.shijianka.reggie.mapper.ShoppingCartMapper;
import cn.shijianka.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}

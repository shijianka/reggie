package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.BaseContext;
import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.ShoppingCart;
import cn.shijianka.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<Dish>> list() {
        return null;
    }

    @Transactional
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据{}", shoppingCart.toString());
        Long userId = BaseContext.get();//获取当前登录用户的id

        Long dishId = shoppingCart.getDishId();//获取提交的菜品Id
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        //获取当前登录用户的购物车数据
        lqw.eq(ShoppingCart::getUserId, userId);
        if (dishId != null) {
            //提交的是菜品
            lqw.eq(ShoppingCart::getDishId, dishId);
        } else {
            //提交的是套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //判断数据库中是否存在该菜品或者套餐
        ShoppingCart one = shoppingCartService.getOne(lqw);
        //如果已经存在，在当前数量的基础上加一
        if (one != null) {
            one.setNumber(one.getNumber() + 1);
            shoppingCartService.updateById(one);
        }
        //如果不存在，则添加到购物车，数量默认就是1
        if (one == null) {
            shoppingCart.setNumber(1);
            one = shoppingCart;
            one.setUserId(userId);
            shoppingCartService.save(one);
        }
        return R.success(one);
    }
}

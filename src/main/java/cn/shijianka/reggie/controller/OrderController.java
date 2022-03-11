package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.BaseContext;
import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.entity.Orders;
import cn.shijianka.reggie.entity.ShoppingCart;
import cn.shijianka.reggie.service.OrdersService;
import cn.shijianka.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据:{}", orders.toString());
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Orders::getUserId,BaseContext.get());
        ordersService.page(ordersPage, lqw);
        return R.success(ordersPage);
    }
}
